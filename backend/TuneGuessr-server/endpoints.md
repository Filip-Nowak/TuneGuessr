# Endpoints list

| Endpoint                                 | Method       | Description                                     |
| ---------------------------------------- | ------------ | ----------------------------------------------- |
| /api/auth/register                       | `POST`       | saves user to database and sends email to user  |
| /api/auth/authenticate                   | `POST`       | return jwt token                                |
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
| /api/user/{nickname}                     | ` GET`       | returns user info by nickname token             |

<br>

- **bolded** endpoints with 🔒 are available to authenticated users
- challenge `GET` endpoints accepts `show` params, that will specify response format. If no params are provided, response will contain all fields

<br>
<br>
<br>

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

```json
/* if registration was successful */

{
    "errorMessage":null,
    "data": null,
}


/* if problem concerns provided fields */
{
    "errorMessage":"password:Password must be between 6 and 40 characters;email:email is invalid",
    "data":null,
}
```

### additional info

if everything went well server will save user to database and send verification email. Account will be disabled until user verifies their email

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

```json
/* if credentials were valid */

{
    "errorMessage":null,
    "data": {
        "token":""
    },
}


/* if credentials were invalid */
{
    "errorMessage":"invalid credentials",
    "data":null,
}
```

<br>
<br>
<br>

## `GET /api/challenge`

### response

```json
    /*if no show params provided*/
    {
        "errorMessage":null,
        "data":{
            "id":0,
            "author":"",
            "songs":[],
            "name":"",
            "description":""
        }
    }



    /*
        if all fields aren't required you can add show params
        e.g. resopnse of
        /api/challenge?show=id&show=name
    */
    {
        "errorMessage":null,
        "data":{
            "id":0,
            "name":""
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

Returns list of matching challenges by name.You can also add show params to format the response

<br>
<br>
<br>

## `POST /api/challenge` 🔒

### body

```json
//description is optional
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

```json
//return song's challenge
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

## `PUT  /api/challenge/{id}/song/{songId}` 🔒

### body

```json
{
  "title": "",
  "artist": "",
  "url": ""
}
```

### repsonse

```json
//return modified song's challenge
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

deletes song by it's number in challenge

<br><br><br>

## `DELETE  /api/ challenge/{id}/song/{songId}` 🔒

### response

```json
//return deleted song's challenge
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
