#!/bin/bash

export CLASSPATH=classes


# delete all classes
rm -rf classes/*
for file in `ls lib` ; do export  CLASSPATH=$CLASSPATH:lib/$file; done


export CLASSPATH=classes
javac -cp $CLASSPATH -d classes src/com/*.java

for file in `ls lib` ; do export  CLASSPATH=$CLASSPATH:lib/$file; done
for file in `ls build_time_libs` ; do export  CLASSPATH=$CLASSPATH:build_time_libs/$file; done

java -cp $CLASSPATH -DoutputDirectory=classes org.javalite.instrumentation.Main


export CLASSPATH=classes
for file in `ls lib` ; do export  CLASSPATH=$CLASSPATH:lib/$file;echo $CLASSPATH; done

java -cp $CLASSPATH -DoutputDirectory=$OUTDIR com.application.Bootstrap