document.addEventListener('DOMContentLoaded', () => {
    const token = window.localStorage.getItem("token");

    const addCourseForm = document.getElementById('addCourseForm');

    addCourseForm.addEventListener('submit', (event) => {
        event.preventDefault(); // Prevent default form submission
        
        const formData = new FormData(addCourseForm);
        const courseData = {
            name: formData.get('name'),
            category: formData.get('category'),
            capacity: formData.get('capacity'),
            duration: formData.get('duration')
        };

        fetch('http://localhost:7000/course/addcourse', {
            method: 'POST',
            headers: {
                'Authorization': `${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(courseData)
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.course) {
                alert("Course added successfully!");
                // window.location.href = 'adminDashboard.html';
            } else {
                console.error('Error adding course:', data.message || 'Unknown error');
            }
        })
        .catch(error => console.error('Error:', error));
    });

    // Add event listener for course management button
    const courseManagementBtn = document.getElementById('courseManagement');
    courseManagementBtn.addEventListener('click', () => {
        window.location.href = './addCourse.html';
    });
});
