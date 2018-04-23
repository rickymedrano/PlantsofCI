#!/usr/bin/env python3
import os
import xml.etree.ElementTree as ET
import xml.dom.minidom as md
import csv



# database location: /Users/traviswight/Desktop/Android/PlantsOfCI/app/src/main/res/values
# csv location: /Users/traviswight/Downloads
# Attribute_Database.tsv


class CSVAppend:
    def __init__(self, csvname, csvdir, root):
        self.csvname = csvname
        self.csvdir = csvdir
        self.root = root

    def massAppend(self):
        os.chdir(self.csvdir)
        csvfile = open(self.csvname, 'r')

        reader = csv.reader(csvfile, delimiter='\t')

        skip = True

        for row in reader:
            print(row)
            os.chdir(self.csvdir)
            print(len(row))
            if skip != True:
                append = Append(2, self.root)
                append.getValues(row)
                append.append()
            skip = False

class Append:
    def __init__(self, mode, root):
        self.mode = mode
        self.root = root
        os.chdir(self.root)
        self.entries = {"plantid": "",
                        "commonname": "",
                        "speciesname": "",
                        "origin": "",
                        "flowercolor": "",
                        "bloomseason": "",
                        "height": "",
                        "width": "",
                        "drought": "",
                        "location": "",
                        "gps": "",
                        "pictureid": ""}
        self.tree = ET.parse("database.xml")
        self.root = self.tree.getroot()

    def getValues(self, values):
        keys = list(self.entries.keys())

        print("-- Value Input --")
        for i in range(len(keys)):
            if self.mode == 1:
                data = input("<%s> tag value: " % keys[i])
            else:
                data = values[i]
            self.entries[keys[i]] = data

        print()

    def append(self):
        # Change Directory To XML Location
        print(os.curdir)
        keys = list(self.entries.keys())
        entry = ET.Element('entry')

        for i in range(len(keys)):
            sub = ET.SubElement(entry, keys[i])
            sub.text = self.entries[keys[i]]

        self.root.append(entry)
        self.tree.write('database.xml')

class Format:
    def __init__(self):
        self.tree = ET.parse("database.xml")
        self.root = self.tree.getroot()

    def getRoot(self):
        return self.root

    def formatXML(self, elem):
        raw = ET.tostring(elem, 'utf-8')
        formatted = '\n'.join(line for line in md.parseString(raw).toprettyxml(indent="\t").split("\n") if line.strip())
        return formatted

def changeDirectory():
    root = ""
    while True:
        path = input("input directory (type help for directory format or cancel to cancel): \n> ")

        if path == "help":
            print("windows: C:\\Users\\user\\dir\\etc\\etc...")
            print("mac: /Users/user/dir/etc/etc...\n")
        elif path == "cancel":
            break
        else:
            os.chdir(path)
            print("path changed to: %s\n" % path)
            root = path
            break
    return root

def main():
    changes = False

    # root directory
    root = os.curdir

    # keep false unless debugging
    DEBUG = False

    while True:
        mode = int(input("-- Data Entry Options --\n(1) Add manual entry\n(2) CSV entry\n(3) Change Root Directory\n(4) Quit\n> "))
        print()
        if mode == 2:
            print("-- Change to CSV File Directory --")
            csvdir = changeDirectory()
            csvname = input("CSV File Name:\n> ")
            csvappend = CSVAppend(csvname, csvdir, root)

            csvappend.massAppend()

            os.chdir(root)
            changes = True

        elif mode == 3:
            root = changeDirectory()
        elif mode == 4:
            break
        elif mode > 4:
            print("invalid input\n")
        else:
            if not changes:
                changes = True

            append = Append(mode, root)
            append.getValues(values=None)
            append.append()

            if mode == 2:
                break

    if changes or DEBUG:
        xml = Format()
        formatted = xml.formatXML(xml.getRoot())
        file = open('database.xml', 'w')
        file.write(formatted)
        file.close()

if __name__ == '__main__':
    main()
