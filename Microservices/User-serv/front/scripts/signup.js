document.getElementById('signupForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const data = Object.fromEntries(formData.entries());

    // Check if the selected role is Instructor and years of experience is 0
    if (data.role === 'Instructor' && parseInt(data.yearsOfExperience) === 0) {
        alert('Please choose an experience level greater than 0 for Instructors.');
        return; // Stop further execution
    }

    try {
        const response = await fetch('http://localhost:3001/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (response.ok) {
            alert('Sign up successful!');
            // Redirect based on role
            if (data.role === 'Admin') {
                window.location.href = './admin.htmll';
            } else if (data.role === 'Instructor') {
                window.location.href = './instructor.html';
            } else if (data.role === 'Student') {
                window.location.href = './student.html';
            } else {
                window.location.href = '/';
            }
        } else {
            if (response.status === 409) { // HTTP 409 Conflict
                alert('Error: Email already exists');
            } else {
                alert(`Error: ${result.message}`);
            }
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred. Please try again.');
    }
});
