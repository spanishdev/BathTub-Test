## BATHTUB TEST

This is an Android application which simulates a Bathtub control (it reads the water temperature and the liters filled).

## The test: Automation Simulator

Preconditions
Please read this in conjunction with the Programming Test Candidate Briefing.
Your home has been automated and connected to the Internet allowing you to control various appliances. The bath can be controlled using a remote device but restricted to turning the hot and cold taps on and off. In order to fill the bath to a preferred temperature and level a simulation program is required that is run from your smart phone. Please use iOS_bathtub_test.psd for creative implementation.

## Input

It is known that bath has a capacity of 150 litres to just below the overflow. This capacity allows a person to get in to the bath without water going down the overflow.

The cold water tap has a flow of 12 Litres per minute. at 10 degrees Celsius.

The hot water tap has a flow of 10 Litres per minute.

The temperature of the water is taken from the boiler web server which returns RESTful JSON. The test JSON is attached and should be run from a test web server http://localhost/bath.json.

##	Assumptions & Constraints
When either tap is on it is at full rate and constant temperature (There is no warm up time or reduced flow).
Output

At any point in time the system should be able to show the capacity and temperature of the bath.

##	Assumptions & Constraints
The bath and your home are super-efficient and there is no cooling of the bath over time.
Expected interface

It should be possible to turn on each tap individually via your touch screen. The interface should show the state of the bath at any time. A Photoshop file has been attached for the design


Bath.json:
{
	hot_water: 50,
	cold_water: 10
}

Hint: bath.json needs to live on a server and a network request needs to be implemented in the app.

