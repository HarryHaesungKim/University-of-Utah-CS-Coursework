# Place your imports here
import signal
from optparse import OptionParser
import re
import sys
from socket import *
from _thread import *
import threading
from datetime import date, datetime

# Global variables
enableCache = False
enableBlocklist = False
badCache = dict()
badBlocklist = []

def ctrl_c_pressed(signal, frame):
    """A signal handler for pressing ctrl-c
    """

    sys.exit(0)

def getIfModSinceCurrDate():
    """Generates the If-Modified-Since header for HTTP for the current date.

    Returns:
        str: The If-Modified-Since header for HTTP.
    """

    dt = datetime.now()
    currHTTPdate = "If-Modified-Since: " + dt.strftime('%a, %d %b %Y %H:%M:%S GMT')
    return currHTTPdate


def badRequest(soc):
    """Sends a bad request http response back to the client and closes the client.

    Args:
        soc (Socket): The socket of the client.
    """

    soc.send(b'HTTP/1.0 400 Bad Request\r\n\r\n')
    soc.close()

def talkToServer(address, port, requestToSendToServer):
    """Sends a request to the server and gets the server response.

    Args:
        address (str): The address to send to.
        port (str): the port to send to.
        requestToSendToServer (str): The request to send.

    Returns:
        bytes: The data from the server.
    """

    # Connect to the server.
    with socket(AF_INET, SOCK_STREAM) as s:
        s.connect((address, int(port)))
        s.send(bytes(requestToSendToServer, 'utf-8'))

        # Get the response from the server.
        totalData = b''
        while True:
            serverReply = s.recv(16384)
            if serverReply:
                totalData = totalData + serverReply
            else:
                break
        s.close()
    return totalData

def handleClient(conn):
    """Connects client to the server, relays request, and gets response back.

    Args:
        conn (Socket): The socket for the client.
    """

    with conn:

        print(f"Connected by {addr}", flush=True)

        # Info needed to be extracted.
        fullURL = ""
        headers = []
        data = b''

        while True:
            print("In while loop.", flush=True)
            data = data + conn.recv(8192)
            print(data, flush=True)
            foundEnd = data.find(b'\r\n\r\n')
            print(foundEnd,flush=True)
            print(b'\r\n\r\n')
            if foundEnd > 0:
                break

        # For testing.
        data = data.decode('utf-8')
        print(">" + data + "<", flush=True)

        # Strip data before splitting. That way, no empty strings at the end of the list.
        dataParts = data.strip().split("\r\n")

        # For testing.
        print(dataParts, flush=True)

        # dataParts[0] should always be the request. The rest should be headers

        # If it's in request format <Request type> <URL> <HTTP version>
        if re.match("[^\s]+\s[^\s]+\s[^\s]+$", dataParts[0]):
            print("request format entered.", flush=True)
            
            # Split by space.
            requestLine = dataParts[0].split(" ")

            # If <Request type> is not a GET request, throw 501.
            if "GET" not in requestLine[0]:
                conn.send(b'HTTP/1.0 501 Not Implemented\r\n\r\n')
                conn.close()
                print('Not "GET" in first line.',flush=True)
                #continue
                return

            # If http:// isn't in the <URL>
            elif "http://" not in requestLine[1]:
                badRequest(conn)
                print('HTTP/1.0 != dataParts[2]',flush=True)
                #continue
                return

            # If anything other than HTTP/1.0 is the <HTTP version>
            elif "HTTP/1.0" not in requestLine[2]:
                badRequest(conn)
                print('HTTP/1.0 != dataParts[2]',flush=True)
                #continue
                return

            # Request format is good to go.
            else:
                fullURL = requestLine[1][7:len(requestLine[1])]
        
        wrongHeaderError = False

        # Loop through the rest of the headers and add them to headers except for "Connection" headers.
        for i in range(1,len(dataParts)):

            # Also, be checking for header format.
            if not re.match("^[^\s]+\:\s.+$", dataParts[i]):
                badRequest(conn)
                print('Incorrect header format.',flush=True)
                wrongHeaderError = True

                # Break out of for-loop
                continue

            if "Connection" not in dataParts[i]:
                headers.append(dataParts[i])

        # Break out of while loop and continue accepting clients.
        if wrongHeaderError:
            #continue
            return

        # Extracting address and path from full URL.
        urlParts = fullURL.split('/')
        addressFromClient = urlParts[0]
        pathFromClient = fullURL[len(urlParts[0]): len(fullURL)]

        # For testing.
        print("The path is: " + pathFromClient, flush=True)

        # If there is no path, throw not implemented error.
        if '/' not in pathFromClient:
            badRequest(conn)
            print('No path. Not even "/"',flush=True)
            return

        # I would imagine enabling/disabling cache would happen here since it would be in the path.
        # For any of these requests, your proxy must respond to the client with an “200 OK” response.

        # If the path has 'proxy', then it's trying to access the cache or the blocklist.
        if "proxy" in pathFromClient:

            # Making sure flags stay changed.
            global enableCache
            global enableBlocklist
            global badBlocklist

            # Cache commands.
            if "cache" in pathFromClient:
                if "enable" in pathFromClient:
                    enableCache = True
                    print("enabled cache",flush=True)
                elif "disable" in pathFromClient:
                    enableCache = False
                    print("disabled cache",flush=True)
                elif "flush" in pathFromClient:
                    badCache.clear()
                    print("flushed cache",flush=True)

            # Blocklist commands.
            if "blocklist" in pathFromClient:
                if "enable" in pathFromClient:
                    enableBlocklist = True
                    print("enabled blocklist",flush=True)
                elif "disable" in pathFromClient:
                    enableBlocklist = False
                    print("disabled blocklist",flush=True)
                elif "add" in pathFromClient:
                    # Getting the string to block from the path.
                    pathParts = pathFromClient.split('/')

                    # For testing.
                    print(pathParts[len(pathParts) - 1], flush=True)

                    stringToBlock = pathParts[len(pathParts) - 1]
                    stringToBlock.strip()
                    if stringToBlock not in badBlocklist:
                        badBlocklist.append(stringToBlock)
                    print("Added to blocklist",flush=True)
                elif "remove" in pathFromClient:
                    # Getting the string to unblock from the path.
                    pathParts = pathFromClient.split('/')

                    # For testing.
                    print(pathParts[len(pathParts) - 1], flush=True)

                    stringToUnblock = pathParts[len(pathParts) - 1]
                    stringToUnblock.strip()
                    if stringToUnblock in badBlocklist:
                        badBlocklist.remove(stringToUnblock)
                    print("Removed from blocklist",flush=True)
                elif "flush" in pathFromClient:
                    badBlocklist.clear()
                    print("flushed blocklist",flush=True)

            # Sending response back to client and closing the connection.
            conn.send(b'HTTP/1.0 200 OK\r\n\r\n')
            conn.close()
            return
        
        # For testing.
        print("In blocklist currently: ", flush=True)
        print(badBlocklist, flush=True)

        # Check blocklist.
        if enableBlocklist:
            for item in badBlocklist:
                if item in fullURL:
                    conn.send(b'HTTP/1.0 403 Forbidden\r\n\r\n')
                    conn.close()
                    return

        # Extracting port from address
        addressAndPort = addressFromClient.split(':')
        addressFromClient = addressAndPort[0]
        portFromClient = ""

        # Checking for a port. If there is none, defualt to port 80.
        if len(addressAndPort) == 2:
            portFromClient = addressAndPort[1]
        else:
            portFromClient = "80"
        
        # Testing.
        print("", flush=True)
        print("The address for server is: " + addressFromClient, flush=True)
        print("The port for server is: " + portFromClient, flush=True)
        print("The path for server is: " + pathFromClient, flush=True)

        # Begin building the request to the server.
        requestToSendToServer = "GET " + pathFromClient + " " + requestLine[2] + "\r\n" + "Host: " + addressFromClient + "\r\n" + "Connection: close\r\n"

        # Adding headers to server request.
        for header in headers:
            requestToSendToServer = requestToSendToServer + header + "\r\n"
        
        # For cache stuff. Too much of a pain to remove '\r\n' from end. It's easier this way.
        oldRequestToServer = requestToSendToServer

        # Add one more "\r\n" to make "\r\n\r\n" to the end.
        requestToSendToServer = requestToSendToServer + "\r\n"

        # For testing.
        print("The request to the server is: >" + requestToSendToServer + "<", flush=True)

        # If cache is enabled.
        if enableCache:
            print("Cache time.", flush=True)

            # If it is in the cache.
            if requestToSendToServer in badCache.keys():

                # Ask server if what it has in the cache is up to date.
                ifModRequestToServer = oldRequestToServer + getIfModSinceCurrDate() + "\r\n\r\n"
                totalData = talkToServer(addressFromClient, portFromClient, ifModRequestToServer)
                ifMod = totalData.decode()

                # For testing.
                print("Server's response to if modified: " + ifMod, flush=True)

                # If data has not been modified.
                if "304" in ifMod:
                    conn.sendall(badCache[requestToSendToServer])
                    conn.close()
                else:
                    totalData = talkToServer(addressFromClient, portFromClient, requestToSendToServer)
                    conn.sendall(totalData)
                    conn.close()
                    badCache.update({requestToSendToServer:totalData})

                return
            
        # If cache is not enabled or request is not in the cache.
        totalData = talkToServer(addressFromClient, portFromClient, requestToSendToServer)

        # Send server response back to the client. Close client socket.
        print("Done :)", flush=True)
        conn.sendall(totalData)
        conn.close()

        # Update the cache if the cache is enabled.
        if enableCache:
            badCache.update({requestToSendToServer:totalData})

# Start of program execution
# Parse out the command line server address and port number to listen to
parser = OptionParser()
parser.add_option('-p', type='int', dest='serverPort')
parser.add_option('-a', type='string', dest='serverAddress')
(options, args) = parser.parse_args()

port = options.serverPort
address = options.serverAddress
if address is None:
    address = 'localhost'
if port is None:
    port = 2100
    #port = 1234

# Set up signal handling (ctrl-c)
signal.signal(signal.SIGINT, ctrl_c_pressed)

# IMPORTANT!
# Immediately after you create your proxy's listening socket add
# the following code (where "skt" is the name of the socket here):
# skt.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
# Without this code the autograder may cause some tests to fail
# spuriously.

listen_s = socket(AF_INET, SOCK_STREAM)
listen_s.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)

# Accept clients
# Accept clients continuously
# bind only once and accept forever.

listen_s.bind((address, port))
listen_s.listen()

# Step1: Allow client to connect to the proxy.
# Step2: Get the data from the client that it is trying to send to the server.
# Step3: Connect to the server and redirect the data from the client to the server.

while True:
    
    threads = list()

    # Accepting client.
    conn, addr = listen_s.accept()

    #print_lock.acquire()

    # Creating a new thread and starting it.
    # start_new_thread(handleClient, (conn,))

    newThread = threading.Thread(target=handleClient, args=(conn,))
    newThread.start()
    
    # Loop continues and accepts new clients.
    # pass  # TODO: accept and handle connections