🧹 CleaningServiceApp
An Android application for booking professional cleaning services.
CleaningServiceApp is built with Kotlin and Android SDK and uses Firebase Authentication and Cloud Firestore for user authentication and booking data.
📱 Current Features
🔐 User registration with name, email, password, and password confirmation
🔑 Email/password login
♻️ Forgot-password email flow
🏠 Home screen with service categories and popular services
🔎 Search/filter popular services by name
🧹 Service list with category filtering
📄 Service details screen
📅 Booking form with date and time pickers
📝 Customer name, phone, address, and optional note
☁️ Save bookings to Cloud Firestore
✅ Booking success / confirmation screen
📋 Customer booking history
🔄 Real-time booking status updates for customers
🔔 Local notifications when a booking status changes
👤 Profile screen with email display and logout
🛠️ Admin dashboard with booking counts
📦 Admin booking management
🔁 Admin status changes: Pending, Accepted, Completed, Cancelled
🧭 App Flow
Splash
  ↓
Login
  ├── Register
  └── Forgot Password
  ↓
Customer Home
  ├── Categories
  ├── Popular Services
  ├── Search
  └── Book Now
       ↓
   Services
       ↓
   Service Details
       ↓
   Booking Form
       ↓
   Booking Successful

Customer navigation:
Home ─ Support ─ Bookings ─ Profile

Admin flow:
Login
  ↓
Admin Dashboard
  ↓
Booking Management
  ↓
Update booking status
🔥 Firebase
The current project integrates:
Firebase Authentication
Cloud Firestore
Firebase Analytics
Google Services Gradle plugin
Firestore collections used
The source code currently uses:
bookings — stores customer booking records
admins — used to identify admin accounts during login
Booking data model
A booking currently contains:
serviceName
name
phone
address
date
time
note
userId
timestamp
status
The default booking status is:
Pending
Supported admin status values are:
Pending
Accepted
Completed
Cancelled
🛠️ Tech Stack
Technology
Usage
Kotlin
Application development
Android SDK
Android platform
AndroidX
Android support libraries
Material Components
UI components
RecyclerView
Lists
ConstraintLayout
Layouts
Firebase Authentication
User authentication
Cloud Firestore
Booking and admin data
Firebase Analytics
Analytics
Gradle Kotlin DSL
Build configuration
📦 Project Configuration
Current Android configuration in the repository:
Namespace: com.irfan.cleaningserviceapp
Application ID: com.irfan.cleaningserviceapp
Minimum SDK: 24
Target SDK: 37
Compile SDK: 37
Java compatibility: 11
Version: 1.0
Version Code: 1
Firebase dependency management currently uses Firebase BoM 33.5.1.
📂 Project Structure
CleaningServiceApp/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/irfan/cleaningserviceapp/
│           │       ├── adapter/
│           │       ├── model/
│           │       ├── AdminDashboardActivity.kt
│           │       ├── BookingFormActivity.kt
│           │       ├── BookingManagementActivity.kt
│           │       ├── BookingSuccessfulActivity.kt
│           │       ├── ForgotPasswordActivity.kt
│           │       ├── HomeFragment.kt
│           │       ├── LoginActivity.kt
│           │       ├── MainActivity.kt
│           │       ├── NotificationHelper.kt
│           │       ├── ProfileFragment.kt
│           │       ├── RegisterActivity.kt
│           │       ├── ServiceDetailActivity.kt
│           │       ├── ServicesFragment.kt
│           │       └── SupportFragment.kt
│           └── res/
│               ├── drawable/
│               ├── layout/
│               ├── mipmap/
│               ├── values/
│               └── xml/
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
🚀 Setup
1. Clone the repository
git clone https://github.com/irfanOSD/CleaningServiceApp.git
cd CleaningServiceApp
2. Open in Android Studio
Open the cloned project in Android Studio and let Gradle sync.
3. Configure Firebase
The Android application uses Firebase. Create/configure the Firebase project for the package:
com.irfan.cleaningserviceapp
Then place the Firebase configuration file at:
app/google-services.json
Before publishing the repository or an application build, review your Firebase/security configuration and avoid exposing credentials or other sensitive secrets.
4. Enable required Firebase services
The source code depends on:
Firebase Authentication
Cloud Firestore
Firebase Analytics
For authentication, the app currently uses email/password sign-in and registration.
5. Run the app
Connect an Android device or start an emulator, then run the app configuration from Android Studio.
🔐 Authentication & Roles
Customer
After a successful login, the app checks the Firestore admins collection using the authenticated user's UID.
If a matching document exists in admins, the user is sent to the Admin Dashboard.
Otherwise, the user is sent to the customer MainActivity.
Admin
Admins can:
View total bookings
View Pending bookings
View Accepted bookings
View Completed bookings
View Cancelled bookings
Open Booking Management
Change booking status
🔔 Booking Status Notifications
The customer booking screen listens to Firestore changes in real time.
When a previously known booking status changes, the app can display a local Android notification such as:
Accepted
Completed
Cancelled
On Android 13+ the application checks for the POST_NOTIFICATIONS permission.
🔎 Service Data
The current service/category data in the source is sample/local data rather than a fully Firestore-driven catalog.
Current categories include:
Home Cleaning
Office Cleaning
Carpet Cleaning
Window Cleaning
Kitchen Cleaning
Current sample services include:
Home Cleaning
Deep Cleaning
Office Cleaning
Carpet Cleaning
Sofa Cleaning
Bathroom Cleaning
Pest Control
Popular-service data is also currently defined in the app source, including sample prices and ratings.
🧪 Testing
The project includes Android test dependencies for:
JUnit
AndroidX JUnit
Espresso
Automated coverage can be expanded as the application grows.
🔮 Potential Improvements
The current codebase can be extended with:
Firestore-backed service/category management
Real service pricing and availability
Payment integration
Cleaner/staff assignment
Booking cancellation/editing by customers
Push notifications via Firebase Cloud Messaging
Admin authentication hardening / custom claims
Better Firestore security rules
Booking filters and pagination
Service images loaded from remote storage
Reviews and ratings
Analytics dashboards
Profile editing
Better offline/error handling
Automated UI and integration tests
👨‍💻 Developer
irfanOSD
GitHub: https://github.com/irfanOSD
Repository: https://github.com/irfanOSD/CleaningServiceApp
📄 License
No license file is currently defined in the repository.
If this project is intended for public reuse, add a license that matches your intended usage.
⭐ Star the repository if you find it useful.
