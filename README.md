# Employee-Attendance-api
A simple RESTful API for managing employee attendance, built using Kotlin, PostgreSQL, and DropWizard.

## API Endpoints:

- `/employees` - This endpoint return list of employee in the Database.

#### Sample Request Body

```json
{
  "firstName":"Nirmalrajaa",
  "lastName": "K",
  "role":"R2",
  "department": "D1",
  "reportingTo":"EMP-1"
}
```

#### Sample Response

```json
[{
    "id": "32b3fbc9-73a3-46c3-b57a-97aaca0a45b6",
    "firstName": "Deva",
    "lastName": "C",
    "role": "R1",
    "department": "D1",
    "reportingTo": "EMP-1"
  },
  {
    "id": "fe3a9059-e546-448d-a8f4-acb94d87c07b",
    "firstName": "Nirmalrajaa",
    "lastName": "K",
    "role": "R1",
    "department": "D1",
    "reportingTo": "EMP-1"
}]
```


- `/employees/{employee_id}` - This endpoint return particular employees details from the Database.


#### Sample Response

```json
[{
"id": "fe3a9059-e546-448d-a8f4-acb94d87c07b",
"firstName": "Nirmalrajaa",
"lastName": "K",
"role": "R1",
"department": "D1",
"reportingTo": "EMP-1"
}]
```

- `/attendance/` - This endpoint provides the attendance record without any filter i.e it will return all the attendance record of the employees will be given.

#### Sample Response

```json

[
  {
    "attendanceId": "0c270e5d-5a34-4655-ba25-73cf034120c2",
    "employeeId": "EMP-001",
    "checkInDateTime": "2025-08-12 09:00:00",
    "checkOutDateTime": "2025-08-12 17:30:00"
  },
  {
    "attendanceId": "5fe34efd-95de-4e8f-9c94-8d0ba3cf2395",
    "employeeId": "EMP-001",
    "checkInDateTime": "2025-08-13 10:15:00",
    "checkOutDateTime": "2025-08-13 21:00:00"
  },
  {
    "attendanceId": "856968a0-47b0-4a39-9112-3e8587ebdff4",
    "employeeId": "EMP-002",
    "checkInDateTime": "2025-08-12 09:05:00",
    "checkOutDateTime": "2025-08-12 19:30:00"
  },
  {
    "attendanceId": "0d572838-ca9c-4985-a5c5-718ebcddb556",
    "employeeId": "EMP-003",
    "checkInDateTime": "2025-08-12 08:55:00",
    "checkOutDateTime": "2025-08-12 16:00:00"
  },
  {
    "attendanceId": "2c89f67f-c63d-45e3-b287-4678634a737b",
    "employeeId": "EMP-006",
    "checkInDateTime": "2025-08-12 08:00:00",
    "checkOutDateTime": "2025-08-12 23:30:00"
  },
  {
    "attendanceId": "e0e13d27-d9ba-4b71-ad26-ed06e30aedec",
    "employeeId": "EMP-006",
    "checkInDateTime": "2025-08-12 10:45:00",
    "checkOutDateTime": "2025-08-12 23:50:00"
  },
  {
    "attendanceId": "6a05dc1b-f8b1-42c9-8c9f-aaad503a72c5",
    "employeeId": "EMP-004",
    "checkInDateTime": "2025-08-12 09:30:00",
    "checkOutDateTime": "2025-08-12 17:00:00"
  },
  {
    "attendanceId": "6ef6ac95-7846-4897-8aa4-d5060e463c22",
    "employeeId": "24fc2dc2-80b1-42f1-af13-98342f29b226",
    "checkInDateTime": "2025-08-09 19:00:00",
    "checkOutDateTime": null
  }
]
```


- `attendance/addCheckin` - Using "Post" method, create the checkin using the query parameters 'employeeId, checkInTime`

#### Sample Response

```json
{
  "attendanceId": "fb1a2a47-60a3-4f38-95f7-7d459e67694a",
  "employeeId": "24fc2dc2-80b1-42f1-af13-98342f29b226",
  "checkInDateTime": "2025-08-10 19:00:00",
  "checkOutDateTime": null,
  "hoursWorked": 0
}
```
#### Error Statement
```json
{
  "error": "Already checked in for the day (attendanceId=6ef6ac95-7846-4897-8aa4-d5060e463c22)"
}
```

- `employees/addCheckOut` - This endpoint create the checkout for the employee if there is a checkin and null checkout for the day.

#### Sample Response

```json
{
  "attendanceId": "fb1a2a47-60a3-4f38-95f7-7d459e67694a",
  "employeeId": "24fc2dc2-80b1-42f1-af13-98342f29b226",
  "checkInDateTime": "2025-08-10 19:00:00",
  "checkOutDateTime": null,
  "hoursWorked": 0
}
```

# Layers

- #### Model
- #### Resources
- #### Application
- #### Service
- #### DAO (Data Access Object)

## Model

- Here, the classes for Attendance, Employee, Department and Role.
- It also has createEmployeeRequest class for getting the input request as body json.
- In addition, the model class also provides basic validation of inputs.

## Resource

- Resource layer has the endpoints which calls the service layer for validations, processing data and updating database with changes.
- This layer also throws the response of the services and DAO.

## Service

- Service layer is called by the resource layer.
- This layer also helps in validation and sending the validated data to the DAO layer for updating data into the database.

## DAO

- DAO Layer has only the CRUD operations.