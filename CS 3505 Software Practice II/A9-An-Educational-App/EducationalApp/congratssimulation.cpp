/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#include "congratssimulation.h"
#include "box2d/box2d.h"
#include "box2d/b2_body.h"
#include <QColor>
#include <vector>
#include <random>
#include <ctime>
#include <cstdlib>

/**
 * Runs a simulation of lanched confetti
 * @brief CongratsSimulation::CongratsSimulation
 * @param confettiCount
 * @param gravityConstant
 * @param accuracy
 */
CongratsSimulation::CongratsSimulation(int confettiCount, float gravityConstant, int accuracy) : sim(b2Vec2(0, gravityConstant)), simPrecision(accuracy), simulationRate(0.05), simGravity(gravityConstant), simConfetti(confettiCount)
{
    // seed the random based on system time
    std::srand(std::time(NULL));

    for(int i = 0; i < confettiCount; i++){
        // add a strand to the sim
        b2BodyDef bodyDef = b2BodyDef();
        bodyDef.type = b2_dynamicBody;

        float bodyX = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/10) - 5.0f;
        float bodyY = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/5) - 2.5f;
        float bodyVX = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/80) - 40.0f;
        float bodyVY = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/8) - 2.0f;

        bodyDef.position = b2Vec2(bodyX, bodyY);
        bodyDef.linearVelocity = b2Vec2(bodyVX, bodyVY);


        b2Body *body = sim.CreateBody(&bodyDef);

        b2PolygonShape dynamicBox;
        dynamicBox.SetAsBox(1.0f,1.0f);

        b2FixtureDef fixtureDef;
        fixtureDef.shape = &dynamicBox;

        fixtureDef.density = 1.0f;

        // Override the default friction.
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;
        // Add the shape to the body.
        body->CreateFixture(&fixtureDef);
    }
}

/**
 * Move the simulation along
 * @brief CongratsSimulation::AdvanceSimulation
 */
void CongratsSimulation::AdvanceSimulation()
{
    sim.Step(simulationRate, simPrecision, simPrecision);
}

/**
 * Get the current state of the confetti
 * @brief CongratsSimulation::getConfetti
 * @return
 */
std::vector<Confetti> CongratsSimulation::getConfetti()
{
    std::vector<Confetti> out;

    b2Body *it = sim.GetBodyList();

    while(it != nullptr)
    {
        Confetti c;
        c.X = it->GetPosition().x;
        c.Y = it->GetPosition().y;
        c.Rotation = it->GetAngle();

        out.push_back(c);

        it = it->GetNext();
    }

    return out;
}

/**
 * Resets the simulation
 * @brief CongratsSimulation::reset
 */
void CongratsSimulation::reset(){

    auto it = sim.GetBodyList();

    while(it != nullptr)
    {
        float bodyX = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/4) - 2.0f;
        float bodyY = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/5);
        float bodyVX = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/80) - 40.0f;
        float bodyVY = static_cast <float> (std::rand()) / static_cast <float> (RAND_MAX/10) - 30.0f;

        it->SetTransform(b2Vec2(bodyX, bodyY), 0);
        it->SetLinearVelocity(b2Vec2(bodyVX, bodyVY));

        it = it->GetNext();
    }
}
