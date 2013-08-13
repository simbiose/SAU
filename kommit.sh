#!/bin/sh

#echo the first parameter is $1
#echo the second parameter is $2
#echo the collection of ALL parameters is $*

clear

git add -A 
git commit -a -m "$*"

