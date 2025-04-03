const API_LINK="https://localhost:8080/";
export async function requestData({
    url,
    method = 'GET',
    body = null,
    auth=false,
}) {
    const options = {

        method: method,
        headers: {
            'Content-Type': 'application/json',  
        },
    };
    if (body) {
        options.body = JSON.stringify(body);
    }
    if (auth) {
        const token = localStorage.getItem('token');
        if (token) {
            options.headers['Authorization'] = `Bearer ${token}`;
        } else {
            throw new Error('No token found in localStorage');
        }
    }

        const response = await fetch(API_LINK + url, options);
        if(response.status === 401 || response.status === 403) {
            localStorage.removeItem('token');
        }
        if (!response.ok) {
            throw new Error(`Error: ${response.status} ${response.statusText}`);
        }
        return await response.json();
    
}

