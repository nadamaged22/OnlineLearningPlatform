# Functional Requirements
# Admin Capabilities
Admins in the system can:

- View and manage user accounts, including students and instructors.
- Review course content before it's published to ensure quality and compliance with platform guidelines.
- Edit or remove courses that violate policies or are deemed inappropriate.
- Track platform usage by students and instructors, and check course popularity, ratings, and reviews.

- # Instructor Capabilities
- Instructors can:

- Register and login into the system. Registration collects information like name, email, password, affiliation, years of experience, and bio.
- Create courses, where each course has a name, duration, category, rating, capacity, number of enrolled students, and a list of reviews.
- View detailed information about each course and search courses by name or category or sort by ratings.
- Accept or reject student enrollments.

# Student Capabilities
Students can:

- Register and login into the system. Registration collects information like name, email, password, affiliation, and bio.
- View current and past course enrollments.
- View detailed information about each course and search courses by name or category or sort by ratings.
- Make or cancel course enrollments. Enrollments are handled in a special way to avoid server failure situations.
- Get notified of course enrollment updates.
- Make a review and rating for a course.

# Technologies Used
- Frontend: HTML, CSS
  
- Backend:
EJB Part: Java EE (Enterprise JavaBeans)
Microservice Part: Node.js
Database: MongoDB

# Setup and Installation
# Prerequisites
- Java Development Kit (JDK) installed
- Node.js installed
- MongoDB installed

# Steps
- Clone the repository:git clone https://github.com/your-username/online-learning-application.git
cd online-learning-application
- Setup MongoDB:
  Ensure MongoDB is running on your local machine or on a remote server. Update the connection string in the microservices configuration file if necessary.
  
  - Setup EJB part:
  - Open the EJB part in your preferred IDE.
- Build the project and deploy it on an application server (e.g., GlassFish, WildFly)

