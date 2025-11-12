// Funciones comunes para AlpesCab Frontend
const API_BASE_URL = ''; // Usamos la misma URL ya que frontend y backend están en el mismo servidor

// Función para verificar si el backend está corriendo
async function checkBackendConnection() {
    try {
        const response = await fetch('/actuator/health', { 
            method: 'GET',
            timeout: 5000 
        });
        return response.ok;
    } catch (error) {
        return false;
    }
}

// Función para mostrar estado de conexión
async function showConnectionStatus() {
    const isConnected = await checkBackendConnection();
    const statusDiv = document.getElementById('connection-status');
    
    if (statusDiv) {
        if (isConnected) {
            statusDiv.innerHTML = '<div class="alert alert-success">✅ Conectado al servidor</div>';
        } else {
            statusDiv.innerHTML = `
                <div class="alert alert-danger">
                    ❌ No se puede conectar al servidor<br>
                    <small>Verifica que la aplicación Spring Boot esté corriendo correctamente</small>
                </div>
            `;
        }
    }
    
    return isConnected;
}

// Función para realizar peticiones HTTP
async function makeRequest(url, options = {}) {
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    
    const finalOptions = { ...defaultOptions, ...options };
    
    try {
        showLoading(true);
        const response = await fetch(`${API_BASE_URL}${url}`, finalOptions);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            return await response.text();
        }
    } catch (error) {
        // Loguear el error en consola. No mostramos un alert global aquí porque
        // muchas páginas cargan selects opcionales y un 404/500 durante la carga
        // no debería inundar al usuario con mensajes. Las páginas deben mostrar
        // errores localizados cuando sea necesario.
        console.error('Error en la petición:', error);
        throw error;
    } finally {
        showLoading(false);
    }
}

// Función para realizar peticiones GET
async function get(url) {
    return makeRequest(url, { method: 'GET' });
}

// Función para realizar peticiones POST
async function post(url, data) {
    if (data instanceof FormData) {
        return makeRequest(url, {
            method: 'POST',
            body: data,
            headers: {} // Dejar que el navegador establezca el content-type para FormData
        });
    }
    
    return makeRequest(url, {
        method: 'POST',
        body: JSON.stringify(data),
    });
}

// Función para realizar peticiones PUT
async function put(url, data) {
    if (data instanceof FormData) {
        return makeRequest(url, {
            method: 'PUT',
            body: data,
            headers: {}
        });
    }
    
    return makeRequest(url, {
        method: 'PUT',
        body: JSON.stringify(data),
    });
}

// Función para realizar peticiones DELETE
async function del(url) {
    return makeRequest(url, { method: 'DELETE' });
}

// Función para mostrar/ocultar indicador de carga
function showLoading(show) {
    let loader = document.getElementById('loading-indicator');
    if (!loader) {
        loader = document.createElement('div');
        loader.id = 'loading-indicator';
        loader.className = 'loading';
        loader.innerHTML = '⏳ Procesando...';
        document.body.appendChild(loader);
    }
    loader.style.display = show ? 'block' : 'none';
}

// Función para mostrar resultados
function showResult(message, isError = false) {
    const resultsDiv = document.getElementById('results');
    if (!resultsDiv) return;
    
    const resultDiv = document.createElement('div');
    resultDiv.className = `result-item ${isError ? 'result-error' : 'result-success'}`;
    resultDiv.innerHTML = `
        <strong>${isError ? 'Error:' : 'Éxito:'}</strong> ${message}
        <button onclick="this.parentElement.remove()" style="float: right; background: none; border: none; font-size: 18px; cursor: pointer;">&times;</button>
    `;
    
    resultsDiv.appendChild(resultDiv);
    
    // Auto-remove after 5 seconds for success messages
    if (!isError) {
        setTimeout(() => {
            if (resultDiv.parentElement) {
                resultDiv.remove();
            }
        }, 5000);
    }
    
    // Scroll to results
    resultsDiv.scrollIntoView({ behavior: 'smooth' });
}

// Función para limpiar resultados
function clearResults() {
    const resultsDiv = document.getElementById('results');
    if (resultsDiv) {
        resultsDiv.innerHTML = '';
    }
}

// Función para convertir FormData a URL parameters
function formDataToParams(formData) {
    const params = new URLSearchParams();
    for (let [key, value] of formData.entries()) {
        if (value !== '' && value !== null && value !== undefined) {
            params.append(key, value);
        }
    }
    return params.toString();
}

// Función para crear tabla HTML desde datos
function createTable(data, headers = null) {
    if (!data) {
        return '<div class="table-container"><p class="alert alert-warning">⚠️ No hay datos disponibles</p></div>';
    }
    
    if (!Array.isArray(data) || data.length === 0) {
        return '<div class="table-container"><p class="alert alert-info">ℹ️ No se encontraron registros</p></div>';
    }
    
    let html = '<div class="table-container"><table class="data-table">';
    
    // Headers
    if (headers) {
        html += '<thead><tr>';
        headers.forEach(header => {
            html += `<th>${header}</th>`;
        });
        html += '</tr></thead>';
    } else if (Array.isArray(data[0])) {
        // Si es array de arrays, usar índices como headers
        html += '<thead><tr>';
        for (let i = 0; i < data[0].length; i++) {
            html += `<th>Columna ${i + 1}</th>`;
        }
        html += '</tr></thead>';
    } else if (typeof data[0] === 'object') {
        // Si es array de objetos, usar las claves como headers
        html += '<thead><tr>';
        Object.keys(data[0]).forEach(key => {
            html += `<th>${key}</th>`;
        });
        html += '</tr></thead>';
    }
    
    // Body
    html += '<tbody>';
    data.forEach(row => {
        html += '<tr>';
        if (Array.isArray(row)) {
            row.forEach(cell => {
                html += `<td>${cell !== null && cell !== undefined ? cell : ''}</td>`;
            });
        } else if (typeof row === 'object') {
            Object.values(row).forEach(cell => {
                html += `<td>${cell !== null && cell !== undefined ? cell : ''}</td>`;
            });
        } else {
            html += `<td>${row}</td>`;
        }
        html += '</tr>';
    });
    html += '</tbody></table></div>';
    
    return html;
}

// Función para formatear fecha
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleString('es-CO');
}

// Función para formatear moneda
function formatCurrency(amount) {
    if (amount === null || amount === undefined) return '';
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP'
    }).format(amount);
}

// Función para validar formulario
function validateForm(formId) {
    const form = document.getElementById(formId);
    if (!form) return false;
    
    const requiredFields = form.querySelectorAll('input[required], select[required], textarea[required]');
    let isValid = true;
    
    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            field.style.borderColor = '#dc3545';
            isValid = false;
        } else {
            field.style.borderColor = '#e9ecef';
        }
    });
    
    return isValid;
}

// Función para resetear formulario
function resetForm(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
        // Resetear estilos de validación
        const fields = form.querySelectorAll('input, select, textarea');
        fields.forEach(field => {
            field.style.borderColor = '#e9ecef';
        });
    }
}

// Función para cargar datos en select
async function loadSelectData(selectId, url, valueKey, textKey) {
    try {
        const data = await get(url);
        const select = document.getElementById(selectId);
        if (select && Array.isArray(data)) {
            select.innerHTML = '<option value="">Seleccione...</option>';
            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item[valueKey];
                option.textContent = item[textKey];
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error(`Error cargando datos para ${selectId}:`, error);
    }
}

// Función para inicializar página
function initializePage() {
    // Cargar datos comunes en selects
    loadSelectData('ciudad_select', '/ciudades/darCiudades', 'id_ciudad', 'nombre');
    loadSelectData('tipo_servicio_select', '/tiposServicio/darTiposServicio', 'id_tipo_servicio', 'nombre');
    loadSelectData('tipo_vehiculo_select', '/tiposVehiculo/darTiposVehiculo', 'id_tipo_vehiculo', 'nombre');
    loadSelectData('nivel_transporte_select', '/nivelesTransporte/darNiveles', 'id_nivel', 'nombre');
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    initializePage();
    
    // Agregar validación en tiempo real a formularios
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        const fields = form.querySelectorAll('input[required], select[required], textarea[required]');
        fields.forEach(field => {
            field.addEventListener('blur', function() {
                if (!this.value.trim()) {
                    this.style.borderColor = '#dc3545';
                } else {
                    this.style.borderColor = '#e9ecef';
                }
            });
        });
    });
});

// Utilidades adicionales
const Utils = {
    // Generar ID único
    generateId: () => Date.now() + Math.random().toString(36).substr(2, 9),
    
    // Debounce para búsquedas
    debounce: (func, wait) => {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },
    
    // Copiar al portapapeles
    copyToClipboard: (text) => {
        navigator.clipboard.writeText(text).then(() => {
            showResult('Texto copiado al portapapeles');
        }).catch(err => {
            console.error('Error al copiar:', err);
        });
    }
};