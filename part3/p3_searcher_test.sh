#!/bin/bash
GRADING_DIR=$HOME/grading
TMP_DIR=/tmp/p3b-grading/
REQUIRED_FILES="build.xml README.txt"

# usage
if [ $# -ne 1 ]
then
     echo "Usage: $0 project3-searcher.zip" 1>&2
     exit 1
fi
ZIP_FILE=$1

# make sure that the script runs on VM
if [ `hostname` != "class-vm" ]; then
     echo "ERROR: You need to run this script within the class virtual machine" 1>&2
     exit 1
fi

# make sure that a lucene index exists in /var/lib/lucene directory
if [[ -n $(ls) ]]; then
    :
else
    echo "ERROR: /var/lib/lucene/ does not contain any lucene index!!!!!"
    echo "ERROR: Before running this script, build your lucene index first."
    exit 1
fi

# create temporary directory used for grading
rm -rf ${TMP_DIR}
mkdir ${TMP_DIR}

#
# check Part B submission
#

# unzip the partb zip file
if [ ! -f ${ZIP_FILE} ]; then
    echo "ERROR: Cannot find $ZIP_FILE" 1>&2
    rm -rf ${TMP_DIR}
    exit 1
fi
unzip -q -d ${TMP_DIR} ${ZIP_FILE}
if [ "$?" -ne "0" ]; then 
    echo "ERROR: Cannot unzip ${ZIP_FILE} to ${TMP_DIR}"
    rm -rf ${TMP_DIR}
    exit 1
fi

# change directory to the partb folder
cd ${TMP_DIR}

# check the existence of the required files
for FILE in ${REQUIRED_FILES}
do
    if [ ! -f ${FILE} ]; then
	echo "ERROR: Cannot find ${FILE} in the root folder of ${ZIP_FILE}" 1>&2
	rm -rf ${TMP_DIR}
	exit 1
    fi
done
JAVA_FILES=`find src -name '*.java' -print`
if [ -z "${JAVA_FILES}" ]; then
    echo "ERROR: No java file is included in src folder of ${ZIP_FILE}" 1>&2
    rm -rf ${TMP_DIR}
    exit 1
fi

# run ant script to build and test your AuctionSearch service
echo "Running 'ant run' to build your searcher and run test..."
ant run

# clean up
rm -rf ${TMP_DIR}

# print out message
echo
echo
echo "Finished checking your Project 3B submission"
echo "Please check the output of this script to ensure a working submission"
exit 0
