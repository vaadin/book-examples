#!/usr/bin/python

import os,re,datetime,sys

# List all revisions
# Returns: list of [revision, datetime] pairs
def fetchRevisions():
    pin = os.popen("svn log|egrep '^r[0-9]+'", "r")
    revlines = pin.readlines()
    pin.close()

    revs = []
    for line in revlines:
        fields = line.split("|")
        rev = int(re.match(r'r([0-9]+)', fields[0]).group(1))
        date = re.match(r' ([^\(]+) \+', fields[2]).group(1)

        # Note that this ignores timezone, the %z doesn't work for some reason
        date = datetime.datetime.strptime(date, "%Y-%m-%d %H:%M:%S")
        revs.append([rev, date])
    return revs

def setToOrderedList(set):
    l = []
    for item in set:
        l.append(item)
    l.sort()
    return l

# Determines which examples are new, removed, or modified
def analyzeDiff(diffLines):
    new     = set()
    deleted = set()
    changed = set()
    for line in diffLines:
        # Example has changed if its start or end tag is changed.
        # This is a bit uncertain way, but easy.
        mchanged = re.search(r'BEGIN-EXAMPLE:\s+(\S+)', line)
        if mchanged:
            changed.add(mchanged.group(1))
        mchanged = re.search(r'END-EXAMPLE:\s+(\S+)', line)
        if mchanged:
            changed.add(mchanged.group(1))

        mnew = re.match(r'^\+\s+new BookExample\("(\S+)"', line)
        if mnew:
            new.add(mnew.group(1))

        mdeleted = re.match(r'^\-\s+new BookExample\("(\S+)"', line)
        if mdeleted:
            deleted.add(mdeleted.group(1))

    # If a "new" example was also deleted, only the title has changed.
    # This does not mean that the example content was changed.
    newtoremove = []
    for item in new:
        if item in deleted:
            newtoremove.append(item)
    for item in newtoremove:
        new.remove(item)

    new = setToOrderedList(new)
    for item in new:
        print "new %s" % (item)

        # New items are new, not just changed
        if item in changed:
            changed.remove(item)

    deleted = setToOrderedList(deleted)
    for item in deleted:
        print "removed %s" % (item)
    
    changed = setToOrderedList(changed)
    for item in changed:
        print "changed %s" % (item)

    return (new, deleted, changed)

def fetchDiffs(revisions, cache):
    for i in xrange(0, 1):
#    for i in xrange(0, len(revisions)-1):
        oldrev = revisions[i+1][0]
        rev = revisions[i][0]
        revdate = revisions[i][1].isoformat()
        print "REVISION %s %s" % (rev, revdate)

        # Get the differences between the two revisions
        pin = os.popen("svn diff -r%d:%d" % (oldrev,rev))
        revlines = pin.readlines()
        pin.close()

        modifications = analyzeDiff(revlines)

        cache[rev] = (rev, revdate, modifications)


revisioncache = {}
revs = fetchRevisions()
fetchDiffs(revs, revisioncache)

print revisioncache
