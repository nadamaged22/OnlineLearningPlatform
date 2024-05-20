document.addEventListener("DOMContentLoaded", function() {
    const courseListDiv = document.getElementById('courseList');
    const token = window.localStorage.getItem('token');

    // Fetch all courses
    fetch('http://localhost:7000/course/getall')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const courses = data.courses; // Accessing the 'courses' array from the JSON
            if (!Array.isArray(courses)) {
                throw new Error('Courses data is not an array');
            }
            courses.forEach(course => {
                const courseDiv = document.createElement('div');
                courseDiv.classList.add('course');

                const courseInfo = document.createElement('div');
                courseInfo.classList.add('course-info');

                const name = document.createElement('p');
                name.textContent = `Name: ${course.name}`;
                const category = document.createElement('p');
                category.textContent = `Category: ${course.category}`;
                const capacity = document.createElement('p');
                capacity.textContent = `Capacity: ${course.capacity}`;
                const duration = document.createElement('p');
                duration.textContent = `Duration: ${course.duration} weeks`;
                const status = document.createElement('p');
                status.textContent = `Status: ${course.status}`;
                const rating = document.createElement('p');
                rating.textContent = `Rating: ${course.rating}`;

                const enrollButton = document.createElement('button');
                enrollButton.classList.add('enroll-btn');
                enrollButton.textContent = 'Enroll';
                enrollButton.addEventListener('click', () => {
                    // Enroll in the course
                    fetch(`http://localhost:8080/FinalOnlineLearning-1.0-SNAPSHOT/api/Course/enrollCourse/${course._id}`, {
                        method: 'POST',
                        headers: {
                            'Authorization': `${token}`
                        }
                    })
                    .then(response => {
                        if(response.ok) {
                            alert('Enrollment successful!');
                        } else {
                            alert('Enrollment failed. Please try again.');
                        }
                    })
                    .catch(error => {
                        console.error('Error enrolling in course:', error);
                        alert('Enrollment failed. Please try again later.');
                    });
                });

                courseInfo.appendChild(name);
                courseInfo.appendChild(category);
                courseInfo.appendChild(capacity);
                courseInfo.appendChild(duration);
                courseInfo.appendChild(rating);
                courseDiv.appendChild(courseInfo);
                courseDiv.appendChild(enrollButton);
                courseListDiv.appendChild(courseDiv);
            });
        })
        .catch(error => console.error('Error fetching courses:', error));
});
