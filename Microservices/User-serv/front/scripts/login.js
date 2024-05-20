document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:3001/auth/signin", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            window.localStorage.setItem("token", data.token);
            // Determine user role from the returned data
            const role = data.role;
            switch (role) {
                case "Admin":
                    alert("Welcome Admin!");
                    window.location.href = "./admin.html";
                    break;
                case "Instructor":
                    alert("Welcome Instructor!");
                    window.location.href = "./instructor.html";
                    break;
                case "Student":
                    alert("Welcome Student!");
                    window.location.href = "./student.html";
                    break;
                default:
                    // Redirect to a default page if role is unknown
                    window.location.href = "/dashboard.html";
                    break;
            }
        } else {
            const data = await response.json();
            // Check if the response indicates invalid data
            if (response.status === 404||response.status === 400) {
                alert("Invalid Data");
            } else {
                document.getElementById("errorMessage").textContent = data.message;
            }
        }
    } catch (error) {
        console.error("Error:", error);
    }
});
