# SOA Microservices Project

A microservices-based transportation management system built with Spring Boot, PostgreSQL, and React.

## 📋 Table of Contents

- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Running the Project](#running-the-project)
- [Services Overview](#services-overview)
- [Troubleshooting](#troubleshooting)

## 🔧 Prerequisites

Before running this project, ensure you have the following installed:

- **Docker** (version 20.10 or higher)
- **Docker Compose** (version 2.0 or higher)
- **Git**

## 📁 Project Structure

```
SOA/
├── backend/
│   ├── docker-compose.yml
│   ├── .env.example
│   ├── GatewayService/
│   ├── authenticationService/
│   ├── subscription-service/
│   ├── TicketManagementService/
│   ├── TripManagementService/
│   └── GeoLocationService/
└── frontend/
    └── (React + Vite application)
```

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone <your-repository-url>
cd SOA
```

### Step 2: Configure Environment Variables

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Copy the example environment file:
   ```bash
   cp .env.example .env
   ```

3. Open the `.env` file and fill in your secrets:
   ```bash
   nano .env
   # or use your preferred editor
   ```

4. Update the following variables with your actual values:

   ```env
   # Database Credentials
   USER_DB=user
   PASSWORD_DB=your_secure_password_here
   
   # JWT Secret (generate a strong random string)
   JWT_SECRET=your_jwt_secret_key_here_min_32_characters
   
   # Email Configuration (for authentication service)
   EMAIL=your-email@example.com
   EMAIL_PASSWORD=your_email_app_password_here
   
   # Stripe API Key (for payment processing)
   SECRET_KEY=sk_test_your_stripe_secret_key_here
   
   # Ticket QR Code Secret
   TICKET_QR_SECRET=your_qr_secret_key_here
   ```

### Step 3: Generate Strong Secrets

For better security, generate strong random secrets:

```bash
# Generate JWT Secret (Linux/Mac)
openssl rand -base64 32

# Generate QR Secret
openssl rand -hex 16
```

### Step 4: Email Configuration

For the authentication service to send emails, you need to:

1. **Gmail Users**: 
   - Enable 2-Factor Authentication
   - Generate an App Password: [Google App Passwords](https://myaccount.google.com/apppasswords)
   - Use the generated app password in `EMAIL_PASSWORD`

2. **Other Email Providers**: 
   - Use your SMTP credentials
   - You may need to update the Spring Boot email configuration in the authentication service

### Step 5: Stripe Configuration

1. Sign up for a Stripe account: [https://stripe.com](https://stripe.com)
2. Get your test API key from the Stripe Dashboard
3. Add it to `SECRET_KEY` in your `.env` file

## 🏃 Running the Project

### Option 1: Run All Services

From the `backend` directory:

```bash
docker compose up
```

Or run in detached mode (background):

```bash
docker compose up -d
```

### Option 2: Run Specific Services

```bash
# Run only the gateway and its dependencies
docker compose up gateway-service

# Run only the frontend
docker compose up frontend
```

### Option 3: Rebuild and Run

If you made changes to the code:

```bash
# Rebuild all services
docker compose up --build

# Rebuild specific service
docker compose up --build gateway-service
```

## 🌐 Services Overview

Once running, the services will be available at:

| Service | URL | Port |
|---------|-----|------|
| **Frontend** | http://localhost:5173 | 5173 |
| **API Gateway** | http://localhost:8080 | 8080 |
| **Authentication Service** | http://localhost:8081 | 8081 |
| **Trip Service** | http://localhost:8082 | 8082 |
| **Ticket Service** | http://localhost:8083 | 8083 |
| **Subscription Service** | http://localhost:8084 | 8084 |
| **Geolocation Service** | http://localhost:8085 | 8085 |

### Database Ports

| Database | Port |
|----------|------|
| Authentication DB | 5433 |
| Subscription DB | 5434 |
| Ticket DB | 5435 |
| Trip DB | 5436 |
| Geolocation DB | 5437 |

## 🔍 Useful Commands

### View Logs

```bash
# View all logs
docker compose logs

# View logs for specific service
docker compose logs gateway-service

# Follow logs in real-time
docker compose logs -f gateway-service
```

### Stop Services

```bash
# Stop all services
docker compose down

# Stop and remove volumes (WARNING: This deletes all data)
docker compose down -v
```

### Restart Services

```bash
# Restart all services
docker compose restart

# Restart specific service
docker compose restart gateway-service
```

### Check Service Status

```bash
docker compose ps
```

### Access Container Shell

```bash
# Access a service container
docker exec -it gateway-service sh

# Access a database container
docker exec -it authentication-db psql -U user -d authentication
```

## 🐛 Troubleshooting

### Issue: "Unable to access jarfile app.jar"

**Solution**: Remove any volume mounts that override the `/app` directory in `docker-compose.yml`

### Issue: Port already in use

**Solution**: Stop the conflicting service or change the port in `docker-compose.yml`

```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### Issue: Database connection refused

**Solution**: 
1. Ensure database containers are running: `docker compose ps`
2. Check database logs: `docker compose logs authentication-db`
3. Wait a few seconds for databases to initialize

### Issue: Services won't start

**Solution**: Clean rebuild
```bash
# Stop everything
docker compose down

# Remove old images
docker compose down --rmi all

# Rebuild from scratch
docker compose up --build
```

### Issue: Email not sending

**Solution**:
1. Verify `EMAIL` and `EMAIL_PASSWORD` in `.env`
2. Check if you're using an App Password (required for Gmail)
3. Check authentication service logs: `docker compose logs authentication-service`

## 📝 Development Notes

### Making Code Changes

1. **Backend Services**: After making changes, rebuild the specific service:
   ```bash
   docker compose build gateway-service
   docker compose up gateway-service
   ```

2. **Frontend**: The frontend uses hot-reload, so changes should reflect automatically

### Database Migrations

Database schemas are managed by Spring Boot's JPA. On first run, tables will be created automatically.

To reset a database:
```bash
# Stop services
docker compose down

# Remove specific volume
docker volume rm soa_authentication_data

# Restart
docker compose up
```

---

**Happy Coding! 🚀**
