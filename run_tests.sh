#!/bin/bash

echo "Starting API Tests..."

# Run API tests
mvn clean test -Dtest=**/apiTests/* || { echo "API Tests Failed"; exit 1; }

echo "Starting UI Tests..."

# Run UI tests
mvn clean test -Dtest=**/seleniumTests/* || { echo "UI Tests Failed"; exit 1; }

echo "Tests Completed!"
