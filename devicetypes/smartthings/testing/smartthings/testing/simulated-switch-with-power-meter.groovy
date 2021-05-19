/**
 *  Copyright 2021 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
 /*
 	Tested with
      https://github.com/arsharpe/SmartThingsPublic/blob/arsharpe-energy-alerts-fixes/smartapps/smartthings/energy-alerts.src on 18 May2021
      https://github.com/arsharpe/SmartThingsPublic/tree/arsharpe-energy-alerts-fixes/smartapps/smartthings/energy-saver.src on 18 May2021
 
 @todo Add energy simulation 
 @todo Finish switch implementation
 @todo Add set power in the mobile app details
 @todo Consider adding going off/on-line
 
 */
 
metadata {
    definition (name: "Simulated Power Sensor - v2", namespace: "smartthings/testing", author: "SmartThings") {
        capability "Power Meter"
        capability "Sensor"
        capability "Health Check"

		capability "Switch"
		capability "Actuator"

		// Custom commands for simulator
        command "up"
        command "down"
        command "setPower", ["number"]
    }

    tiles {
        valueTile("power", "device.power") {
            state("power", label:'${currentValue}', unit:"W")
        }
        standardTile("up", "device.power", inactiveLabel: false, decoration: "flat") {
            state "default", label:'up', action:"up"
        }
        standardTile("down", "device.power", inactiveLabel: false, decoration: "flat") {
            state "default", label:'down', action:"down"
        }
        
  		standardTile("button", "device.switch", canChangeIcon: true) {
			state "off", label: 'Off', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'On', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00A0DC", nextState: "off"
 		}

        main "button"
        details("power","up","down","button")
    }
}

def parse(String description) {
}

def installed() {
    initialize()
}

def updated() {
    initialize()
}

/* The simulator seems to call initialise very rarely */
def initialize() {
	log.debug "initialize"
/*
    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
    sendEvent(name: "healthStatus", value: "online")
    sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
*/

    sendEvent(name:"power", value: 0, unit: "w")
}

def up() {
    setPower(getPower() + 1.0)
}

def down() {
    setPower(getPower() - 1.0)
}

def setPower(value) {
//	log.debug "setPower()"

	sendEvent(name:"power", value: value, unit: "w")
}

private getPower() {
//	log.debug "getPower()"
    
    return device.currentState("power").floatValue
}

//*** Switch Controls
def on() {
	sendEvent(name: "switch", value: "on")
}

def off() {
	sendEvent(name: "switch", value: "off")
}
