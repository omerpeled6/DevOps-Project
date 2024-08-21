import requests
from requests.auth import HTTPBasicAuth
import pytest
import logging

# Base URL for the CI/CD automation server
Base_URL = "http://localhost:8080/api/jobs"

# Authentication setup (username and password)
auth = HTTPBasicAuth('user', '1234')

# 1. Configure the Logger
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

# 2. Create a Logging Utility Function
def log_response(response) -> None:
    try:
        response_body = response.json()
    except ValueError:
        response_body = "No JSON Response"
    logger.info(f'Status Code: {response.status_code}')
    logger.info(f'Response Body: {response_body}')
    return response_body

# Helper function to print all jobs
def print_all_jobs(auth):
    response = requests.get(Base_URL, auth=auth)
    jobs = log_response(response)
    print("\nAll Jobs:")
    for job in jobs:
        print(job)

# 3. Define Test Fixtures (@pytest.fixture)
@pytest.fixture(scope='module')
def setup():
    print("\nSetup for the module")
    yield
    print("\nTeardown for the module")

# 4. Write Test FunctionsF

# Test 1: Get all jobs
def test_get_all_jobs(setup):
    try:
        response = requests.get(Base_URL, auth=auth)
        jobs = log_response(response)
        assert response.status_code == 200, "Failed to get all jobs"
        assert isinstance(jobs, list), "Response body doesn't contain a list"
        # Print the retrieved jobs
        print("\nRetrieved Jobs:")
        for job in jobs:
            print(job)
    except requests.exceptions.RequestException as e:
        logger.error(f"Request failed: {e}")
        assert False, f"Test failed due to request error: {e}"

# Test 2: Create a new job
def test_create_job(setup):
    job_data = {
        "jobName": "New CI/CD Job",
        "status": "Pending",
        "createdAt": "2024-08-15T12:00:00",
        "updatedAt": "2024-08-15T12:00:00",
        "jobType": "Build",
        "password": "securepassword123"
    }
    try:
        response = requests.post(Base_URL, json=job_data, auth=auth)
        job = log_response(response)
        assert response.status_code == 201, "Failed to create a new job"
        # Print the created job
        print("\nCreated Job:")
        print(job)
        # Print all jobs after creation
        print_all_jobs(auth)
    except requests.exceptions.RequestException as e:
        logger.error(f"Request failed: {e}")
        assert False, f"Test failed due to request error: {e}"

# Test 3: Get a job by ID
def test_get_job_by_id(setup):
    job_id = 1  # Replace with a valid job ID
    try:
        response = requests.get(f"{Base_URL}/{job_id}", auth=auth)
        job = log_response(response)
        assert response.status_code == 200, f"Failed to get job with ID: {job_id}"
        # Print the retrieved job
        print(f"\nRetrieved Job with ID {job_id}:")
        print(job)
    except requests.exceptions.RequestException as e:
        logger.error(f"Request failed: {e}")
        assert False, f"Test failed due to request error: {e}"

# Test 4: Update a job's status
def test_update_job_status(setup):
    job_id = 1  # Replace with a valid job ID
    try:
        # Print job before update
        print(f"\nJob with ID {job_id} before update:")
        response = requests.get(f"{Base_URL}/{job_id}", auth=auth)
        job_before_update = log_response(response)
        print(job_before_update)

        # Update job status
        update_data = {
            "jobName": "Updated CI/CD Job",
            "status": "Completed",
            "updatedAt": "2024-08-15T13:00:00",
            "jobType": "Build",
            "password": "newsecurepassword123"
        }
        response = requests.put(f"{Base_URL}/{job_id}", json=update_data, auth=auth)
        job_after_update = log_response(response)
        assert response.status_code == 200, f"Failed to update job with ID: {job_id}"

        # Print job after update
        print(f"\nJob with ID {job_id} after update:")
        print(job_after_update)
    except requests.exceptions.RequestException as e:
        logger.error(f"Request failed: {e}")
        assert False, f"Test failed due to request error: {e}"

# Test 5: Delete a job
def test_delete_job(setup):
    job_id = 1  # Replace with a valid job ID
    try:
        response = requests.delete(f"{Base_URL}/{job_id}", auth=auth)
        log_response(response)
        assert response.status_code == 204, f"Failed to delete job with ID: {job_id}"
        # Print a confirmation of the deletion
        print(f"\nJob with ID {job_id} deleted successfully.")
        # Print all jobs after deletion
        print_all_jobs(auth)
    except requests.exceptions.RequestException as e:
        logger.error(f"Request failed: {e}")
        assert False, f"Test failed due to request error: {e}"

# Main
if __name__ == "__main__":
    pytest.main()