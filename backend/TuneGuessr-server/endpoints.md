# Endpoints list

| Endpoint                                 | Method       | Description                                     |
|------------------------------------------|--------------|-------------------------------------------------|
| /api/auth/register                       | `POST`       | saves user to database and sends email to user  |
| /api/auth/authenticate                   | `POST`       | return jwt token                                |
| /api/auth/confirm?token={token}          | `GET`        | confirms email, accepts `token` param           |
|                                          |              |                                                 |
| /api/challenge/search/{name}             | `GET`        | returns list of matching challenges             |
| **/api/challenge** 🔒                    | **`POST`**   | adds new challenge                              |
| /api/challenge/{id}                      | `GET`        | returns challenge based on id                   |
| **/api/challenge/{id}** 🔒               | **`DELETE`** | deletes challenge based on id                   |
|                                          |              |                                                 |
| **/api/challenge/{id}/song** 🔒          | **`POST`**   | adds song to challenge                          |
| **/api/challenge/{id}/song/{songId}** 🔒 | **`PUT`**    | modifies song based on song number in challenge |
| **/api/challenge/{id}/song/{songId}** 🔒 | **`DELETE`** | deletes song based on song number in challenge  |
|                                          |              |                                                 |
| **/api/user** 🔒                         | **`GET`**    | returns user info by jwt token                  |
| /api/user/{nickname}                     | `GET`        | returns user info by nickname token             |

<br>

- **bolded** endpoints with 🔒 are available to authenticated users
- challenge `GET` endpoints accepts params, that will specify response format. If no params are provided,
  response will contain all fields

<br>
<br>
<br>

# Possible responses

### Authentication response

Used in `/api/auth/authenticate`, `/api/auth/register` and `/api/auth/confirm`. If everything went well,
`status` will be dividable by 10 and `message` will describe the result of the operation and `errors` will be empty. If
there was
a problem `errors` will contain list of errors. More info in [Authentication status](#authentication-status)

```json
{
  "status": 0,
  "message": "",
  "token": "",
  "errors": [
    {
      "status": 0,
      "message": ""
    }
  ]
}
```

### Data response

Used in endpoints that return data. If everything went well, `errorMessage` will be null and `data` will contain
requested data. If there was a problem `errorMessage` will contain error message and `data` will be null.

```json
{
  "errorMessage": "",
  "data": {}
}
```

# Endpoint explication

## `POST /api/auth/register`

### body

```json
{
  "nickname": "",
  "email": "",
  "password": ""
}
```

### response

if registration was successful

```json
{
  "status": 20,
  "message": "email sent",
  "errors": []
}
```

### additional info

if everything went well server will save user to database and send verification email. Account will be disabled until
user verifies their email

<br>
<br>
<br>

## `POST /api/auth/authenticate`

### body

```json
{
  "email": "",
  "password": ""
}
```

### response

if credentials were valid

```json
{
  "status": 30,
  "message": "Authentication successful",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5maWwyOEBnbWFpbC5jb20iLCJpYXQiOjE3MjQ2NzIzMjksImV4cCI6MTcyNDY3Mzc2OX0.UCRaecVvVFcooy0XmxN0UQmK9BH8VFJ8SO6njP9NFbI",
  "errors": []
}
```

<br>
<br>
<br>

## `GET /api/confirm?token={token}`

### response

```json
{
  "status": 40,
  "message": "Email confirmed",
  "errors": []
}
```

### additional info

<br>
<br>
<br>

## `GET /api/challenge`

### response

if no params provided

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "author": "",
    "songs": [],
    "name": "",
    "description": ""
  }
}
```

if all fields aren't required you can add params, e.g. response of /api/challenge?id&name

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "name": ""
  }
}
```

<br>
<br>
<br>

## `GET /api/challenge/search/{name}`

### response

```json
{
  "errorMessage": null,
  "data": [
    {
      "id": 0,
      "author": "",
      "songs": [],
      "name": "",
      "description": ""
    }
  ]
}
```

### additional info

Returns list of matching challenges by name.You can also add params to format the response

<br>
<br>
<br>

## `POST /api/challenge` 🔒

### body

description is optional

```json
{
  "name": "",
  "description": ""
}
```

### response

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "author": "",
    "songs": [],
    "name": "",
    "description": ""
  }
}
```

<br>
<br>
<br>

## `DELETE /api/challenge/{id}` 🔒

### response

```json
{
  "errorMessage": null,
  "data": "Challenge deleted"
}
```

<br>
<br>
<br>

## `POST /api/challenge/{id}/song` 🔒

### body

```json
{
  "title": "",
  "artist": "",
  "url": ""
}
```

### response

return song's challenge

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "author": "",
    "songs": [],
    "name": "",
    "description": ""
  }
}
```

<br>
<br>
<br>

## `PUT /api/challenge/{id}/song/{songId}` 🔒

### body

```json
{
  "title": "",
  "artist": "",
  "url": ""
}
```

### response

return modified song's challenge

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "author": "",
    "songs": [],
    "name": "",
    "description": ""
  }
}
```

### additional info

deletes song by its number in challenge

<br><br><br>

## `DELETE /api/ challenge/{id}/song/{songId}` 🔒

### response

return deleted song's challenge

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "author": "",
    "songs": [],
    "name": "",
    "description": ""
  }
}
```

<br><br><br>

## `GET api/user` 🔒

### response

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "nickname": "",
    "email": "",
    "challengeList": []
  }
}
```

### additional info

returns user data by jwt token

<br><br><br>

## `GET api/user/{nickname}`

### response

```json
{
  "errorMessage": null,
  "data": {
    "id": 0,
    "nickname": "",
    "email": "",
    "challengeList": []
  }
}
```

# Authentication status

If everything went well status will be dividable by 10 (20,30,40). In case of error, `status` will contain error code
and `message`
will contain error message:

```json
{
  "status": 31,
  "message": "Invalid credentials",
  "errors": [
    {
      "status": 31,
      "message": "Invalid credentials"
    }
  ]
}
```

if there were multiple errors, `status` will be 1 and  `errors` will contain list of errors:

```json
{
  "status": 1,
  "message": "Multiple errors",
  "errors": [
    {
      "status": 21,
      "message": "Email already exists"
    },
    {
      "status": 22,
      "message": "Nickname already exists"
    }
  ]
}
```

## Status codes

Errors from `2 to 20` are body format errors, errors from `20 to 29` are registration process errors, errors
from `30 to 39`
are authentication errors and errors from `40 to 49` are email confirmation errors

| Code   | Message                                  |
|--------|------------------------------------------|
| **20** | Account registered and email send        |
| **30** | Authentication successful                |
| **40** | Email confirmed                          |
|        |                                          |
| **1**  | Multiple errors                          |
| **2**  | Nickname is required                     |
| **3**  | Nickname length must be between 3 and 20 |
| **4**  | Email is required                        |
| **5**  | Email is invalid                         |
| **6**  | Password is required                     |
| **7**  | Password length must be between 6 and 40 |
| **21** | Email already exists                     |
| **22** | Nickname already exists                  |
| **31** | Invalid credentials                      |
| **41** | Token not found                          |
| **42** | Token expired                            |
| **43** | Token already confirmed                  |

