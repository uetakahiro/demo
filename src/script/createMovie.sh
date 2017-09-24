#!/bin/sh

id=$1
name=$2
type=$3
viewNum=$4
uri="http://localhost:8080/movies"

curl -X POST \
--header "Content-Type: application/json" \
--data "{\"id\":\"$id\", \"name\":\"$name\", \"type\":\"$type\", \"viewNum\":\"$viewNum\"}" \
$uri
