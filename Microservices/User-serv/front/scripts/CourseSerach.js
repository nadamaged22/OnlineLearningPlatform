async function searchByName() {
    const searchKey = document.getElementById('searchByNameInput').value;
    const response = await fetch(`http://localhost:7000/course/searchname?searchkey=${searchKey}`);
    if (response.ok) {
        const data = await response.json();
        displayResults(data.course);
    } else {
        displayError(response.statusText);
    }
}

async function searchByCategory() {
    const searchKey = document.getElementById('searchByCategoryInput').value;
    const response = await fetch(`http://localhost:7000/course/searchcat?searchkey=${searchKey}`);
    if (response.ok) {
        const data = await response.json();
        displayResults(data.course);
    } else {
        displayError(response.statusText);
    }
}

async function searchByRating() {
    const response = await fetch(`http://localhost:7000/course/searchrate`);
    if (response.ok) {
        const data = await response.json();
        displayResults(data.courses);
    } else {
        displayError(response.statusText);
    }
}

function displayResults(courses) {
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = '';

    if (!courses || courses.length === 0) {
        resultsDiv.innerHTML = '<p>No courses found.</p>';
        return;
    }

    courses.forEach(course => {
        const courseDiv = document.createElement('div');
        courseDiv.classList.add('course');
        courseDiv.innerHTML = `
            <h3>${course.name}</h3>
            <p>Category: ${course.category}</p>
            <p>Rating: ${course.rating}</p>
        `;
        resultsDiv.appendChild(courseDiv);
    });
}

function displayError(error) {
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = `<p>Error: ${error}</p>`;
}
