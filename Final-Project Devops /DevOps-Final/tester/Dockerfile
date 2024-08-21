# Use an official Python runtime as a parent image
FROM python:3.11-slim

# Set the working directory inside the container
WORKDIR /tester

# Copy the requirements file and install any dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the Python test files into the container
COPY . .

# Define environment variable for the test URL
ENV TEST_URL=http://ci-server:8080

# Command to run the tests
CMD ["pytest", "-v"]

