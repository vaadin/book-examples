#!/usr/bin/python

import os,sys

################################################################################
def coordToFloat(coord):
    (deg, m, direction) = coord.split(" ")
    value = int(deg) + int(m)/60.0
    if direction == "S" or direction == "E":
        value = -value
    return value

################################################################################

fin = open("countrycoordinates.txt", "r")
lines = map(lambda x: x.strip(), fin.readlines())
fin.close()

print "HashMap<String, Coordinate> coords = new HashMap<String, Coordinate>();"
for line in lines:
    (name, lat, lng) = map(lambda x: x.strip(), line.split(";"))
    lat = coordToFloat(lat)
    lng = coordToFloat(lng)
    print "coords.put(\"%s\", new Coordinate(%0.2f, %0.2f));" % (name, lat, lng)
