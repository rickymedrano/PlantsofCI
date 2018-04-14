#!/usr/bin/env python3
import os
import xml.etree.ElementTree as ET
import xml.dom.minidom as md
import csv

# -- FOR TRAVIS --
# test database location: /Users/traviswight/Desktop/PlantsOfCIDatabaseAppend
# database location: /Users/traviswight/Desktop/Android/PlantsOfCI/app/src/main/res/values
# tsv location: /Users/traviswight/Downloads
# Attribute_Database.tsv

# Export TSV file from excel
class TSVAppend:
    def __init__(self, tsvname, tsvdir, root, entries):
        self.tsvname = tsvname
        self.tsvdir = tsvdir
        self.root = root
        self.entires = entries

        # Wipe the current database (we're overriding )
        wipe = EditDatabase(self.root, self.tsvdir, self.tsvname)
        wipe.wipeDatabase()

    def massAppend(self):
        os.chdir(self.tsvdir)
        tsvfile = open(self.tsvname, 'r')
        reader = csv.reader(tsvfile, delimiter='\t')

        skip = True

        for row in reader:
            print(row)
            os.chdir(self.tsvdir)
            print(len(row))

            if skip != True:
                append = Append(2, self.root, self.entires)
                append.getValues(row)
                append.append()
            skip = False

class Append:
    def __init__(self, mode, root, entries):
        self.mode = mode
        self.root = root
        os.chdir(self.root)
        self.entries = entries
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

class EditDatabase:
    def __init__(self, root, tsvdir, tsvname):
        self.root = root
        self.tsvdir = tsvdir
        self.tsvname = tsvname

    def editClient(self):
        changes = False

        while True:
            print("-- Edit Database Options --")
            print("(1) Edit Entry")
            print("(2) Delete Entry")
            print("(3) Wipe Database")
            print("(4) Exit Edit Database")

            mode = int(input("> "))
            print()

            if mode == 1:
                commonname = input("insert entry name (<commonname>): ")
                tag = input("insert name of tag being changed: ")
                changes = self.editEntry(commonname, tag)
            elif mode == 2:
                commonname = input("insert entry name (common name): ")
                changes = self.deleteEntry(commonname)
            elif mode == 3:
                changes = self.wipeDatabase()
                print("Returning To Data Entry Options\n")
                break
            elif mode == 4:
                break
            else:
                print("invalid input")

            print()

        return changes

    def editEntry(self, commonname, tag):
        changes = False

        os.chdir(self.root)
        tree = ET.parse("database.xml")
        root = tree.getroot()

        for entry in root.findall("entry"):
            foundCommonName = entry.find("commonname").text
            foundTag = entry.find(tag)

            if foundCommonName == commonname:
                foundTag.text = input("insert new value for <%s>: \n> " % tag)
                tree.write("database.xml")
                changes = True
                break

        return changes

    def deleteEntry(self, commonname):
        changes = False

        os.chdir(self.root)
        tree = ET.parse("database.xml")
        root = tree.getroot()

        for entry in root.findall("entry"):
            foundCommonName = entry.find("commonname").text

            if foundCommonName == commonname:
                root.remove(entry)
                print("<entry> with <commonname> %s deleted..." % commonname)
                tree.write("database.xml")
                changes = True
                break

        return changes

    def wipeDatabase(self):
        os.chdir(self.root)
        tree = ET.parse("database.xml")
        root = tree.getroot()

        for entry in root.findall("entry"):
            root.remove(entry)

        tree.write("database.xml")

        print("database wiped...")
        return True

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

def formatFile():
    xml = Format()
    formatted = xml.formatXML(xml.getRoot())
    file = open("database.xml", 'w')
    file.write(formatted)
    file.close()

def main():
    changes = False

    # directories and file names:
    root = os.curdir
    tsvdir = os.curdir
    tsvname = "filename.tsv"
    entries = {"plantid": "",
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

    # keep false unless debugging
    DEBUG = True

    while True:
        print("-- Data Entry Options --")
        print("(1) Add manual entry")
        print("(2) TSV entry")
        print("(3) Change Root Directory (important)")
        print("(4) Edit Database")
        print("(5) Format File")
        print("(6) Quit")

        mode = int(input("> "))
        print()

        if mode == 1:
            if not changes:
                changes = True

            append = Append(mode, root, entries)
            append.getValues(values=None)
            append.append()

            if mode == 2:
                break
        elif mode == 2:
            print("-- Change to CSV File Directory --")
            tsvdir = changeDirectory()
            tsvname = input("CSV File Name:\n> ")
            tsvappend = TSVAppend(tsvname, tsvdir, root, entries)

            tsvappend.massAppend()

            os.chdir(root)
            changes = True
        elif mode == 3:
            root = changeDirectory()
        elif mode == 4:
            os.chdir(root)
            edit = EditDatabase(root, tsvdir, tsvname)
            changes = edit.editClient()
        elif mode == 5:
            formatFile()
        elif mode == 6:
            print("rosebud")
            break
        else:
            print("invalid input\n")

    if changes or DEBUG:
        formatFile()

if __name__ == '__main__':
    main()
