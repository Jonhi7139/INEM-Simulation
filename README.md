# INEM SOS — Emergency Management System
 
A console-based emergency dispatch and management system inspired by **INEM** (Instituto Nacional de Emergência Médica — Portugal's national emergency medical services). The system allows staff to register emergencies, automatically dispatch the nearest ambulance and hospital, and track the lifecycle of each occurrence.
 
---
 
## Project Structure
 
```
INEM_SOS/
├── Main.java        # Entry point, menus, game loop, file I/O
└── DataBase.java    # Data models (Worker, Emergency, Ambulance, Hospital, etc.)
```
 
### Persistent Data Files (auto-generated at runtime)
 
| File             | Contents                          |
|------------------|-----------------------------------|
| `workers.dat`    | Registered staff accounts         |
| `amb.dat`        | Registered ambulances             |
| `hosp.dat`       | Hospitals and available beds      |
| `ongoin.dat`     | Ongoing emergencies               |
| `pending.dat`    | Pending emergencies (no resources)|
| `concluded.dat`  | Resolved emergencies              |
| `haem.dat`       | Hospital–ambulance assignments    |
 
All files use Java's **object serialization** (`ObjectOutputStream` / `ObjectInputStream`).
 
---
 
## How to Compile & Run
 
**Requirements:** Java 9+
 
```bash
# Compile
javac -d out INEM_SOS/DataBase.java INEM_SOS/Main.java
 
# Run
java -cp out INEM_SOS.Main
```
 
---
 
## Default Credentials
 
On first run, an **ADMIN** account is created automatically:
 
| Field    | Value          |
|----------|----------------|
| Name     | `ADMIN`        |
| Email    | `admin@inem.pt`|
| Password | `#X1234`       |
 
---
 
## User Roles
 
### ADMIN
Manages the system's resources.
 
| Option | Action                          |
|--------|---------------------------------|
| 1      | Add a worker account            |
| 2      | Remove a worker account         |
| 3      | View all occurrences (all staff)|
| 4      | Register a new ambulance        |
| 5      | Remove an ambulance             |
| 6      | List all ambulances             |
| 7      | List all hospitals and beds     |
| 8      | Exit to main menu               |
 
> Worker emails must end in `@inem.pt`. The ADMIN account cannot be deleted.
 
---
 
### Worker
Handles day-to-day emergency dispatch.
 
| Option | Action                                           |
|--------|--------------------------------------------------|
| 1      | Register a new emergency                         |
| 2      | Resolve an ongoing emergency                     |
| 3      | Resolve a pending emergency (when resources free)|
| 4      | View own ongoing and concluded emergencies       |
| 5      | View own pending emergencies                     |
| 6      | Add a bed to a hospital                          |
| 7      | Exit to main menu                                |
 
---
 
## Emergency Lifecycle
 
When a worker registers an emergency, the system automatically:
 
1. Finds the **nearest available ambulance** (using GPS coordinates).
2. Finds the **nearest hospital with a free bed** (using GPS coordinates).
3. Assigns both and marks the emergency as **Ongoing** — or **Pending** if either resource is unavailable.
```
New Emergency
     │
     ├─ Ambulance + Hospital available ──► ONGOING
     │                                        │
     │                                        └─ Worker resolves ──► CONCLUDED
     │
     └─ Missing resource ──────────────► PENDING
                                             │
                                             └─ Resources freed ──► ONGOING ──► CONCLUDED
```
 
When an ongoing emergency is resolved, its ambulance is automatically freed (marked available again).
 
---
 
## Distance Calculation
 
The system uses the **Haversine formula** to compute the real-world distance (in km) between two GPS coordinates, ensuring the closest ambulance and hospital are always dispatched.
 
```java
distance(lat1, lon1, lat2, lon2)  // Returns km between two points
```
 
---
 
## Pre-loaded Hospitals (Coimbra region)
 
| # | Hospital                                                              |
|---|-----------------------------------------------------------------------|
| 1 | Centro Hospitalar e Universitário de Coimbra – Polo Hospital Geral Covões |
| 2 | Centro Hospitalar e Universitário de Coimbra – Polo HUC               |
| 3 | Hospital da Luz Coimbra                                               |
| 4 | Hospital CUF Coimbra                                                  |
 
Each hospital starts with **2 available beds**. Beds can be added manually by workers (option 6).
 
---
 
## Data Model — `DataBase.java`
 
A single base class with **5 inner static subclasses**, all `Serializable`:
 
| Class          | Represents         | Key Fields                                        |
|----------------|--------------------|---------------------------------------------------|
| `Worker`       | Staff account      | name, email, password                             |
| `Em`           | Emergency record   | description, GPS coords, patient name, health no., timestamp, status, hospital, ambulance |
| `Amb`          | Ambulance          | plate, GPS coords, availability status            |
| `Hosp`         | Hospital           | name, GPS coords, available beds                  |
| `HAem`         | Assignment record  | hospital name, ambulance plate (linked to an `Em`)|
 
---
 
## Known Limitations / Possible Improvements
 
- `DataBase` uses a single god-class design with many unrelated fields — splitting into separate, clearly named classes would improve readability and maintainability.
- Variable names like `name`, `name2` ... `name7` make the code hard to follow; more descriptive names would help.
- Passwords are stored in **plain text** — hashing (e.g. SHA-256) should be used.
- No admin view of individual worker histories; only global occurrence lists.
- Ambulance coordinates are randomly generated within the Coimbra urban area bounding box at registration time and never updated after dispatch.
- Pending emergencies are not automatically re-checked when resources become free; a worker must manually trigger resolution (option 3).
