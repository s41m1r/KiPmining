#!/bin/bash

#git log --pretty="* [%an](mailto:%ae)%n* [%cn](mailto:%ce)" | sort | uniq > contributors.md


CONTRIBUTORS=`git log --pretty="* [%an](mailto:%ae)%n* [%cn](mailto:%ce)" | sort | uniq`

echo "Contributors:$CONTRIBUTORS"

NEWCONTRIB=`echo "# Contributors"$'\n\n'"$CONTRIBUTORS"`

echo "New Contributors:\n$NEWCONTRIB"

csplit README.md '/# Contributors/'

echo "$NEWCONTRIB" > xx01

cat xx0* > README.md
rm xx0*

