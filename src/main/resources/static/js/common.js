const API_BASE_URL = "";

async function apiRequest(url, options = {}) {

    const response = await fetch(API_BASE_URL + url, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        }
    });

    if (!response.ok) {
        throw new Error("Request failed");
    }

    return response.json();
}