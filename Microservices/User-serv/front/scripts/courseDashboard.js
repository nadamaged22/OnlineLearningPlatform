// scripts/courseDashboard.js

document.addEventListener('DOMContentLoaded', () => {
    const token = window.localStorage.getItem("token");

    // Fetch courses from backend with token in headers
    fetch('http://localhost:7000/course/getall', {
        headers: {
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data && data.courses) {
            displayCourses(data.courses);
        } else {
            console.error('No courses found');
        }
    })
    .catch(error => console.error('Error:', error));

    // Function to display courses
    function displayCourses(courses) {
        const courseListContainer = document.getElementById('courseList');
        courseListContainer.innerHTML = ''; // Clear previous content

        courses.forEach(course => {
            const courseDiv = document.createElement('div');
            courseDiv.classList.add('course');

            // Display each field of the course
            for (const key in course) {
                if (course.hasOwnProperty(key)) {
                    const value = course[key];
                    const field = document.createElement('p');
                    field.textContent = `${capitalizeFirstLetter(key)}: ${value}`;
                    courseDiv.appendChild(field);
                }
            }

            courseListContainer.appendChild(courseDiv);
        });
    }

    // Helper function to capitalize the first letter of a string
    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }
});
