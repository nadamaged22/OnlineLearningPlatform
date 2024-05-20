document.addEventListener('DOMContentLoaded', () => {
    const courseList = document.getElementById('courseList');
    const token = window.localStorage.getItem('token'); 

    // Function to fetch all courses
    const getAllCourses = () => {
        fetch('http://localhost:7000/course/getall')
        .then(response => response.json())
        .then(data => {
            if (data && data.courses) {
                // Clear previous course list
                courseList.innerHTML = '';

                // Loop through courses and create course elements
                data.courses.forEach(course => {
                    const courseElement = document.createElement('div');
                    courseElement.classList.add('course');

                    // Create course details
                    const courseDetails = document.createElement('div');
                    courseDetails.classList.add('course-details');
                    courseDetails.innerHTML = `
                        <h2>${course.name}</h2>
                        <p><strong>Duration:</strong> ${course.duration}</p>
                        <p><strong>Category:</strong> ${course.category}</p>
                        <p><strong>Capacity:</strong> ${course.capacity}</p>
                    `;

                    // Create edit button
                    const editButton = document.createElement('button');
                    editButton.innerText = 'Edit';
                    editButton.addEventListener('click', () => {
                        // Redirect to edit page with course ID as parameter
                        window.location.href = `editCourse.html?id=${course._id}`;
                    });

                    // Create delete button
                    const deleteButton = document.createElement('button');
                    deleteButton.innerText = 'Delete';
                    deleteButton.addEventListener('click', () => {
                        // Send a request to delete the course
                        fetch(`http://localhost:7000/course/deletecourse/${course._id}`, {
                            method: 'DELETE',
                            headers: {
                                'Authorization': `${token}`, // Add your authorization token here
                                'Content-Type': 'application/json'
                            }
                        })
                        .then(response => {
                            if (response.ok) {
                                // Course deleted successfully, remove the course element from the UI
                                courseElement.remove();
                            } else {
                                // Course deletion failed, log the error
                                console.error('Failed to delete course');
                            }
                        })
                        .catch(error => console.error('Error:', error));
                    });
                    
                    // Append details and buttons to course element
                    courseElement.appendChild(courseDetails);
                    courseElement.appendChild(editButton);
                    courseElement.appendChild(deleteButton);
                    
                    // Append course element to course list
                    courseList.appendChild(courseElement);

                    // Append details and buttons to course element
                    courseElement.appendChild(courseDetails);
                    courseElement.appendChild(editButton);
                    courseElement.appendChild(deleteButton);

                    // Append course element to course list
                    courseList.appendChild(courseElement);
                });
            } else {
                console.error('No course data retrieved');
            }
        })
        .catch(error => console.error('Error:', error));
    };

    // Call getAllCourses function to initially populate the course list
    getAllCourses();
});
