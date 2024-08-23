/*
 * friendlist.c - [Starting code for] a web-based friend-graph manager.
 *
 * Based on:
 *  tiny.c - A simple, iterative HTTP/1.0 Web server that uses the 
 *      GET method to serve static and dynamic content.
 *   Tiny Web server
 *   Dave O'Hallaron
 *   Carnegie Mellon University
 */
#include "csapp.h"
#include "dictionary.h"
#include "more_string.h"

static void doit(int fd);
static dictionary_t *read_requesthdrs(rio_t *rp);
static void read_postquery(rio_t *rp, dictionary_t *headers, dictionary_t *d);
static void clienterror(int fd, char *cause, char *errnum, 
                        char *shortmsg, char *longmsg);
static void print_stringdictionary(dictionary_t *d);
static void serve_request(int fd, char* body);

// My global variables
static dictionary_t* friendlist;
pthread_mutex_t lock;

// My methods
static void serve_greet(int fd, dictionary_t *query);
// TODO:
static void print_friends(int fd, dictionary_t *query);
static void befriend(int fd, dictionary_t *query);
static void unfriend(int fd, dictionary_t *query);
static void introduce(int fd, dictionary_t *query);

int main(int argc, char **argv) {
  int listenfd, connfd;
  char hostname[MAXLINE], port[MAXLINE];
  socklen_t clientlen;
  struct sockaddr_storage clientaddr;

  // Initialize the friendlist.
  friendlist = make_dictionary(COMPARE_CASE_SENS, free);

  // Initialize mutex lock
  pthread_mutex_init(&lock, NULL);

  /* Check command line args */
  if (argc != 2) {
    fprintf(stderr, "usage: %s <port>\n", argv[0]);
    exit(1);
  }

  listenfd = Open_listenfd(argv[1]);

  /* Don't kill the server if there's an error, because
     we want to survive errors due to a client. But we
     do want to report errors. */
  exit_on_error(0);

  /* Also, don't stop on broken connections: */
  Signal(SIGPIPE, SIG_IGN);

  while (1) {
    clientlen = sizeof(clientaddr);
    connfd = Accept(listenfd, (SA *)&clientaddr, &clientlen);
    if (connfd >= 0) {
      Getnameinfo((SA *) &clientaddr, clientlen, hostname, MAXLINE, 
                  port, MAXLINE, 0);
      printf("Accepted connection from (%s, %s)\n", hostname, port);
      doit(connfd);
      Close(connfd);
    }
  }
}

/*
 * doit - handle one HTTP request/response transaction
 */
void doit(int fd) {
  char buf[MAXLINE], *method, *uri, *version;
  rio_t rio;
  dictionary_t *headers, *query;

  

  /* Read request line and headers */
  Rio_readinitb(&rio, fd);
  if (Rio_readlineb(&rio, buf, MAXLINE) <= 0)
    return;
  printf("%s", buf);
  
  if (!parse_request_line(buf, &method, &uri, &version)) {
    clienterror(fd, method, "400", "Bad Request",
                "Friendlist did not recognize the request");
  } else {
    if (strcasecmp(version, "HTTP/1.0")
        && strcasecmp(version, "HTTP/1.1")) {
      clienterror(fd, version, "501", "Not Implemented",
                  "Friendlist does not implement that version");
    } else if (strcasecmp(method, "GET")
               && strcasecmp(method, "POST")) {
      clienterror(fd, method, "501", "Not Implemented",
                  "Friendlist does not implement that method");
    } else {
      headers = read_requesthdrs(&rio);

      /* Parse all query arguments into a dictionary */
      query = make_dictionary(COMPARE_CASE_SENS, free);
      parse_uriquery(uri, query);
      if (!strcasecmp(method, "POST"))
        read_postquery(&rio, headers, query);

      /* For debugging, print the dictionary */
      print_stringdictionary(query);

      /* You'll want to handle different queries here,
         but the intial implementation always returns
         nothing: */
      if (starts_with("/greet", uri)){
        pthread_mutex_lock(&lock);
        serve_greet(fd, query);
        pthread_mutex_unlock(&lock);
      }

      else if (starts_with("/friends", uri)){
        pthread_mutex_lock(&lock);
        print_friends(fd, query);
        pthread_mutex_unlock(&lock);
      }

      else if (starts_with("/befriend", uri)){
        pthread_mutex_lock(&lock);
        befriend(fd, query);
        pthread_mutex_unlock(&lock);
      }

      else if (starts_with("/unfriend", uri)){
        pthread_mutex_lock(&lock);
        unfriend(fd, query);
        pthread_mutex_unlock(&lock);

        // pthread_mutex_lock(&lock);
        // unfriend2(fd, query);
        // pthread_mutex_unlock(&lock);
      }

      else if (starts_with("/introduce", uri)){
        pthread_mutex_lock(&lock);
        introduce(fd, query);
        pthread_mutex_unlock(&lock);
      }
      
      else
        // serve_request(fd, query);
        serve_request(fd, "");

      /* Clean up */
      free_dictionary(query);
      free_dictionary(headers);
    }

    /* Clean up status line */
    free(method);
    free(uri);
    free(version);
  }
}

/*
 * read_requesthdrs - read HTTP request headers
 */
dictionary_t *read_requesthdrs(rio_t *rp) {
  char buf[MAXLINE];
  dictionary_t *d = make_dictionary(COMPARE_CASE_INSENS, free);

  Rio_readlineb(rp, buf, MAXLINE);
  printf("%s", buf);
  while(strcmp(buf, "\r\n")) {
    Rio_readlineb(rp, buf, MAXLINE);
    printf("%s", buf);
    parse_header_line(buf, d);
  }
  
  return d;
}

void read_postquery(rio_t *rp, dictionary_t *headers, dictionary_t *dest) {
  char *len_str, *type, *buffer;
  int len;
  
  len_str = dictionary_get(headers, "Content-Length");
  len = (len_str ? atoi(len_str) : 0);

  type = dictionary_get(headers, "Content-Type");
  
  buffer = malloc(len+1);
  Rio_readnb(rp, buffer, len);
  buffer[len] = 0;

  if (!strcasecmp(type, "application/x-www-form-urlencoded")) {
    parse_query(buffer, dest);
  }

  free(buffer);
}

static char *ok_header(size_t len, const char *content_type) {
  char *len_str, *header;
  
  header = append_strings("HTTP/1.0 200 OK\r\n",
                          "Server: Friendlist Web Server\r\n",
                          "Connection: close\r\n",
                          "Content-length: ", len_str = to_string(len), "\r\n",
                          "Content-type: ", content_type, "\r\n\r\n",
                          NULL);
  free(len_str);

  return header;
}

/*
 * serve_request - example request handler
 */
static void serve_request(int fd, char* body) {
  size_t len;
  char *header;

  // body = strdup("alice\nbob");

  len = strlen(body);

  /* Send response headers to client */
  header = ok_header(len, "text/html; charset=utf-8");
  Rio_writen(fd, header, strlen(header));
  printf("Response headers:\n");
  printf("%s", header);

  free(header);

  /* Send response body to client */
  Rio_writen(fd, body, len);

  // free(body);
}

// My functions ------------------------------------------------------------------------------------------------------------------------

/*
 * serve_greet - greeting clietns done in the lab
 */
static void serve_greet(int fd, dictionary_t *query) {
  size_t len;
  char *body, *header;

  char* user = dictionary_get(query, "user");

  body = append_strings("Greetings, ", user, "!", NULL);

  len = strlen(body);

  /* Send response headers to client */
  header = ok_header(len, "text/html; charset=utf-8");
  Rio_writen(fd, header, strlen(header));
  printf("Response headers:\n");
  printf("%s", header);

  free(header);

  /* Send response body to client */
  Rio_writen(fd, body, len);

  free(body);
}

/*
 * TODO:
 * print_friends - Prints the friends of the client.
 */
static void print_friends(int fd, dictionary_t *query) {
  char *body;

  char* user = dictionary_get(query, "user");

  dictionary_t *usersFriends = dictionary_get(friendlist, user);

  printf("usersFriends dictionary pointer 2: %p\n", usersFriends);

  // If user doesn't exist, return nothing. Empty string. Don't create a new dictionary for user.
  if(!usersFriends){
    // Add a dictionary to the friendlist.
    // usersFriends = make_dictionary(COMPARE_CASE_SENS, free);
    // dictionary_set(friendlist, user, usersFriends);
    body = append_strings("", NULL);
  }
  else{
    // print_stringdictionary(usersFriends);
    printf("usersFriends count: %ld\n", dictionary_count(usersFriends));
      // If this user has any friends.
    if(dictionary_count(usersFriends) > 0){

      // body should be an empty string right now.
      // Loop through friends and append them to the body to be sent back to the client.

      // printf("Working1...");

      body = join_strings(dictionary_keys(usersFriends), '\n');
      // Join strings not append_strings
      //for(int i = 1; i < dictionary_count(usersFriends); i++){
      //  body = join_strings(body, dictionary_key(usersFriends, i), "\n");
      // }
      // printf("Working2...");
    }

    else{
      body = append_strings("", NULL);
    }
  }

  serve_request(fd, body);
}

static void befriend(int fd, dictionary_t *query) {
  char *body;
  char* user = dictionary_get(query, "user");
  dictionary_t *usersFriends = dictionary_get(friendlist, user);

  if(!usersFriends){
    // Add a dictionary to the friendlist.
    usersFriends = make_dictionary(COMPARE_CASE_SENS, free);
    dictionary_set(friendlist, user, usersFriends);
  }

  printf("usersFriends dictionary pointer 1: %p\n", usersFriends);

  char* friendsToBe = dictionary_get(query, "friends");
  
  printf("Friends: %s\n", friendsToBe);
  
  char** friendsToAdd = split_string(friendsToBe, '\n');

  // for(int loop = 0; loop < sizeof(friendsToAdd); loop++)
  //   printf("Within friendsToAdd: %s\n", friendsToAdd[loop]);
  
  // int exitSize = sizeof(friendsToAdd)/sizeof(char*);

  // printf("Exit size: %d", exitSize);

  for(int i = 0; friendsToAdd[i] != NULL; i++){

    printf("Loop number: %d\n", i);
    printf("Current friend: %s\n", friendsToAdd[i]);

    // Adding each friend to usersFriends.
    char* newfriend = dictionary_get(usersFriends, friendsToAdd[i]);

    // If new friend isn't in userFriends.
    if(!newfriend){
      dictionary_set(usersFriends, friendsToAdd[i], 0);
    }
    // Else, do nothing.

    // Now to add user to newfriendsFriends.
    dictionary_t* friendsFriends = dictionary_get(friendlist, friendsToAdd[i]);
    
    // If friend exists in friendlist
    if(!friendsFriends){
      // Add a dictionary to the friendlist.
      friendsFriends = make_dictionary(COMPARE_CASE_SENS, free);
      dictionary_set(friendlist, friendsToAdd[i], friendsFriends);
    }

    // printf("Pointer for dictionary of friend %s: %p\n", friendsToAdd[i], friendsToAdd);

    // Adding user to newfriendsFriends.
    dictionary_set(friendsFriends, user, 0);

    // printf("Friend: %s\n", friendsToAdd[i]);
  }

  printf("friendlist dictionary count: %ld\n", dictionary_count(friendlist));  
  printf("User dictionary count: %ld\n", dictionary_count(usersFriends));  

  body = join_strings(dictionary_keys(usersFriends), '\n');
  //printf("Body text: %s\n", body);
  serve_request(fd, body);
}

static void unfriend(int fd, dictionary_t *query) {
  char *body;
  char *user = dictionary_get(query, "user");
  dictionary_t *usersFriends = dictionary_get(friendlist, user);

  printf("User: '%s'", user);

  if(!usersFriends){
    // Add a dictionary to the friendlist.
    usersFriends = make_dictionary(COMPARE_CASE_SENS, free);
    dictionary_set(friendlist, user, usersFriends);
  }

  printf("usersFriends dictionary pointer 1: %p\n", usersFriends);

  char* exFriends = dictionary_get(query, "friends");
  
  printf("Friends: %s\n", exFriends);
  
  char** friendsToDelete = split_string(exFriends, '\n');

  // for(int loop = 0; loop < sizeof(friendsToAdd); loop++)
  //   printf("Within friendsToAdd: %s\n", friendsToAdd[loop]);
  
  // int exitSize = sizeof(friendsToAdd)/sizeof(char*);

  // printf("Exit size: %d", exitSize);

  for(int i = 0; friendsToDelete[i] != NULL; i++){

    printf("Loop number: %d\n", i);
    printf("Current exfriend: %s\n", friendsToDelete[i]);

    // Remove friend from usersFriend dictionary.
    dictionary_remove(usersFriends, friendsToDelete[i]);

    // Remove user from friendsFriends dictionary.
    dictionary_t* friendsFriends = dictionary_get(friendlist, friendsToDelete[i]);

    dictionary_remove(friendsFriends, user);
  }

  printf("friendlist dictionary count: %ld\n", dictionary_count(friendlist));  
  printf("User dictionary count: %ld\n", dictionary_count(usersFriends));  

  body = join_strings(dictionary_keys(usersFriends), '\n');
  //printf("Body text: %s\n", body);
  serve_request(fd, body);
}

static void introduce(int fd, dictionary_t *query) {
  // 1. For the introduce method, you should:
  // Prepare the request
  // Connect to server
  // Read status and check the status in a loop
  // Add each friend to the user's friend set
  // Send response headers to the client
  // 2. The body is a string with a friends array split by "\n", like friend0 "\n" friend1 "\n"....

  // free method only in introduce.

  char CRLF[2] = "\r\n";

  char *body;
  char buf[MAXLINE];
  rio_t rio;

  // Prepare the request
  char *host = dictionary_get(query, "host");
  char *port = dictionary_get(query, "port");
  char* user = dictionary_get(query, "user");
  char* friend = dictionary_get(query, "friend");

  char* request = append_strings("GET /friends?user=", friend, " HTTP/1.1", CRLF, "Host: ", host, ":", port, CRLF, "Accept: text/html", CRLF, CRLF, NULL);
  printf("Working1");

  // Connect to server
  int clientfd = Open_clientfd(host, port);
  Rio_readinitb(&rio, clientfd);
  printf("Working2");

  while (Fgets(buf, MAXLINE, stdin) != NULL) {

    Rio_writen(clientfd, buf, strlen(buf));
    Rio_readlineb(&rio, buf, MAXLINE);
    Fputs(buf, stdout);

    printf("what is in buf? %s", buf);

    // Read status and check the status in a loop
  }

  // Add each friend to the user's friend set
  // Send response headers to the client

  Close(clientfd);

  printf("Working3");

}


// ------------------------------------------------------------------------------------------------------------------------

/*
 * clienterror - returns an error message to the client
 */
void clienterror(int fd, char *cause, char *errnum, 
		 char *shortmsg, char *longmsg) {
  size_t len;
  char *header, *body, *len_str;

  body = append_strings("<html><title>Friendlist Error</title>",
                        "<body bgcolor=""ffffff"">\r\n",
                        errnum, " ", shortmsg,
                        "<p>", longmsg, ": ", cause,
                        "<hr><em>Friendlist Server</em>\r\n",
                        NULL);
  len = strlen(body);

  /* Print the HTTP response */
  header = append_strings("HTTP/1.0 ", errnum, " ", shortmsg, "\r\n",
                          "Content-type: text/html; charset=utf-8\r\n",
                          "Content-length: ", len_str = to_string(len), "\r\n\r\n",
                          NULL);
  free(len_str);
  
  Rio_writen(fd, header, strlen(header));
  Rio_writen(fd, body, len);

  free(header);
  free(body);
}

static void print_stringdictionary(dictionary_t *d) {
  int i, count;

  count = dictionary_count(d);
  for (i = 0; i < count; i++) {
    printf("%s=%s\n",
           dictionary_key(d, i),
           (const char *)dictionary_value(d, i));
  }
  printf("\n");
}
