document.addEventListener("DOMContentLoaded", function() {
    const enrollmentsListDiv = document.getElementById('enrollmentsList');
    const token = window.localStorage.getItem('token');

    // Fetch past enrollments
    fetch('http://localhost:8080/FinalOnlineLearning-1.0-SNAPSHOT/api/Course/ViewCurrentEnrollement', {
        method: 'GET',
        headers: {
            'Authorization': `${token}`
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(enrollments => {
        enrollments.forEach(enrollment => {
            const enrollmentDiv = document.createElement('div');
            enrollmentDiv.classList.add('enrollment');

            const enrollmentInfoDiv = document.createElement('div');
            enrollmentInfoDiv.classList.add('enrollment-info');

            const status = document.createElement('p');
            status.textContent = `Status: ${enrollment.status}`;
            status.classList.add(`status-${enrollment.status.toLowerCase()}`); // Apply status-specific CSS class

            const courseId = document.createElement('p');
            courseId.textContent = `Course ID: ${enrollment.courseId}`;

            const studentId = document.createElement('p');
            studentId.textContent = `Student ID: ${enrollment.studentId}`;

            enrollmentInfoDiv.appendChild(status);
            enrollmentInfoDiv.appendChild(courseId);
            enrollmentInfoDiv.appendChild(studentId);

            enrollmentDiv.appendChild(enrollmentInfoDiv);
            enrollmentsListDiv.appendChild(enrollmentDiv);
        });
    })
    .catch(error => console.error('Error fetching past enrollments:', error));
});
