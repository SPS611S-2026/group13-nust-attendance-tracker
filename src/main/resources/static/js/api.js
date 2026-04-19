/* ============================================================
   api.js — shared fetch helpers + auth utilities
   ============================================================ */

const API = (() => {

  const BASE = '';   // same origin

  function getToken()  { return localStorage.getItem('token'); }
  function getUser()   { return JSON.parse(localStorage.getItem('user') || 'null'); }
  function setSession(token, user) {
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
  }
  function clearSession() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  function requireAuth(allowedRoles) {
    const token = getToken();
    const user  = getUser();
    if (!token || !user) { window.location.href = '/login.html'; return; }
    if (allowedRoles && !allowedRoles.includes(user.role)) {
      alert('Access denied.'); window.location.href = '/login.html';
    }
  }

  async function request(method, path, body) {
    const headers = { 'Content-Type': 'application/json' };
    const token = getToken();
    if (token) headers['Authorization'] = 'Bearer ' + token;

    const res = await fetch(BASE + path, {
      method,
      headers,
      body: body ? JSON.stringify(body) : undefined
    });

    if (res.status === 401) { clearSession(); window.location.href = '/login.html'; return; }
    if (res.status === 204) return null;

    const data = await res.json().catch(() => null);
    if (!res.ok) throw new Error(data?.error || `Request failed (${res.status})`);
    return data;
  }

  async function download(path, filename) {
    const headers = {};
    const token = getToken();
    if (token) headers['Authorization'] = 'Bearer ' + token;
    const res = await fetch(BASE + path, { headers });
    if (!res.ok) throw new Error('Download failed');
    const blob = await res.blob();
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url; a.download = filename; a.click();
    URL.revokeObjectURL(url);
  }

  return {
    getToken, getUser, setSession, clearSession, requireAuth,
    get:    (path)         => request('GET',    path),
    post:   (path, body)   => request('POST',   path, body),
    put:    (path, body)   => request('PUT',    path, body),
    patch:  (path)         => request('PATCH',  path),
    delete: (path)         => request('DELETE', path),
    download
  };
})();
