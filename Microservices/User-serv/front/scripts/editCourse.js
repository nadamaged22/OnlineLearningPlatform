document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const courseId = urlParams.get('id');
    const token = window.localStorage.getItem('token');

    const editCourseForm = document.getElementById('editCourseForm');

    // Fetch course details based on the provided ID
    fetch(`http://localhost:7000/course/viewcourse/${courseId}`, {
        headers: {
            'Authorization': `${token}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(course => {
        console.log(course.course.name)

        // Pre-fill the form fields with course details
        document.getElementById('name').value = course.course.name;
        document.getElementById('duration').value = course.course.duration;
        document.getElementById('category').value = course.course.category;
        document.getElementById('capacity').value = course.course.capacity;
    })
    .catch(error => console.error('Error fetching course details:', error));

    // Handle form submission
    editCourseForm.addEventListener('submit', event => {
        event.preventDefault();

        // Gather updated course details from form fields
        const updatedCourse = {
            name: document.getElementById('name').value,
            duration: document.getElementById('duration').value,
            category: document.getElementById('category').value,
            capacity: document.getElementById('capacity').value
        };

        // Send updated course details to backend for editing
        fetch(`http://localhost:7000/course/updateCourse/${courseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`
            },
            body: JSON.stringify(updatedCourse)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data.message);
            // Redirect or handle success as needed
        })
        .catch(error => console.error('Error updating course:', error));
    });
});
