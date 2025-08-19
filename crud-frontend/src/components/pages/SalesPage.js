import React, { useState, useEffect } from 'react';
import { Plus, Edit, Trash2, X, Eye, ShoppingCart, User, UserCheck } from 'lucide-react';

const SalesPage = () => {
  const [sales, setSales] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const [editingSale, setEditingSale] = useState(null);
  const [selectedSale, setSelectedSale] = useState(null);
  const [formData, setFormData] = useState({
    customerId: '',
    employeeId: '',
    details: []
  });

  const API_BASE = 'http://localhost:8080/api';

  // Cargar todos los datos al montar el componente
  useEffect(() => {
    fetchAllData();
  }, []);

  // Obtener todos los datos necesarios
  const fetchAllData = async () => {
    try {
      setLoading(true);
      const [salesRes, customersRes, employeesRes, productsRes] = await Promise.all([
        fetch(`${API_BASE}/sales`),
        fetch(`${API_BASE}/customers`),
        fetch(`${API_BASE}/employees`),
        fetch(`${API_BASE}/products`)
      ]);

      if (salesRes.ok) setSales(await salesRes.json());
      if (customersRes.ok) setCustomers(await customersRes.json());
      if (employeesRes.ok) setEmployees(await employeesRes.json());
      if (productsRes.ok) setProducts(await productsRes.json());
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  // Recargar solo las ventas
  const fetchSales = async () => {
    try {
      const response = await fetch(`${API_BASE}/sales`);
      if (response.ok) {
        const data = await response.json();
        setSales(data);
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  // Limpiar formulario
  const resetForm = () => {
    setFormData({
      customerId: '',
      employeeId: '',
      details: []
    });
    setEditingSale(null);
  };

  // Abrir modal para nueva venta
  const handleAdd = () => {
    resetForm();
    addProductToSale(); // Agregar una línea de producto por defecto
    setShowModal(true);
  };

  // Ver detalles de venta
  const handleViewDetails = (sale) => {
    setSelectedSale(sale);
    setShowDetailsModal(true);
  };

  // Cerrar modales
  const handleCloseModal = () => {
    setShowModal(false);
    setShowDetailsModal(false);
    resetForm();
    setSelectedSale(null);
  };

  // Manejar cambios en el formulario
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  // Agregar producto a la venta
  const addProductToSale = () => {
    setFormData(prev => ({
      ...prev,
      details: [
        ...prev.details,
        { productId: '', quantity: 1 }
      ]
    }));
  };

  // Eliminar producto de la venta
  const removeProductFromSale = (index) => {
    setFormData(prev => ({
      ...prev,
      details: prev.details.filter((_, i) => i !== index)
    }));
  };

  // Actualizar detalle de producto
  const updateProductDetail = (index, field, value) => {
    setFormData(prev => ({
      ...prev,
      details: prev.details.map((detail, i) => 
        i === index ? { ...detail, [field]: value } : detail
      )
    }));
  };

  // Crear venta
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Validar que hay productos
    if (formData.details.length === 0) {
      alert('Debe agregar al menos un producto');
      return;
    }

    // Validar que todos los productos tienen cantidad > 0
    const invalidDetails = formData.details.some(detail => 
      !detail.productId || detail.quantity <= 0
    );
    
    if (invalidDetails) {
      alert('Todos los productos deben tener un ID válido y cantidad mayor a 0');
      return;
    }

    try {
      const response = await fetch(`${API_BASE}/sales`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          customerId: parseInt(formData.customerId),
          employeeId: parseInt(formData.employeeId),
          details: formData.details.map(detail => ({
            productId: parseInt(detail.productId),
            quantity: parseInt(detail.quantity)
          }))
        })
      });

      if (response.ok) {
        await fetchSales();
        handleCloseModal();
        alert('Venta creada exitosamente');
      } else {
        alert('Error al crear la venta');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error al crear la venta');
    }
  };

  // Eliminar venta
  const handleDelete = async (saleId) => {
    if (window.confirm('¿Estás seguro de que deseas eliminar esta venta?')) {
      try {
        const response = await fetch(`${API_BASE}/sales/${saleId}`, {
          method: 'DELETE'
        });

        if (response.ok) {
          await fetchSales();
          alert('Venta eliminada exitosamente');
        } else {
          alert('Error al eliminar la venta');
        }
      } catch (error) {
        console.error('Error:', error);
        alert('Error al eliminar la venta');
      }
    }
  };

  // Formatear precio
  const formatPrice = (price) => {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP'
    }).format(price);
  };

  // Obtener nombre del producto por ID
  const getProductName = (productId) => {
    const product = products.find(p => p.id === parseInt(productId));
    return product ? `${product.productCode} - ${product.description}` : '';
  };

  return (
    <div className="max-w-7xl mx-auto py-8 px-4">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-3xl font-bold text-gray-800">Gestión de Ventas</h2>
        <button
          onClick={handleAdd}
          className="bg-orange-600 hover:bg-orange-700 text-white px-4 py-2 rounded-lg flex items-center space-x-2 transition-colors"
        >
          <Plus className="h-4 w-4" />
          <span>Nueva Venta</span>
        </button>
      </div>

      {/* Tabla de ventas */}
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        {loading ? (
          <div className="p-8 text-center">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-600 mx-auto"></div>
            <p className="mt-2 text-gray-600">Cargando ventas...</p>
          </div>
        ) : sales.length === 0 ? (
          <div className="p-8 text-center text-gray-500">
            No hay ventas registradas
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    ID Venta
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Cliente
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Empleado
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Total
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Productos
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Acciones
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {sales.map((sale) => (
                  <tr key={sale.saleId} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm font-medium text-gray-900">
                        #{sale.saleId}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center">
                        <User className="h-4 w-4 text-blue-500 mr-2" />
                        <div>
                          <div className="text-sm font-medium text-gray-900">
                            {sale.customer.firstName} {sale.customer.lastName}
                          </div>
                          <div className="text-sm text-gray-500">
                            {sale.customer.documentType} {sale.customer.documentNumber}
                          </div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center">
                        <UserCheck className="h-4 w-4 text-green-500 mr-2" />
                        <div>
                          <div className="text-sm font-medium text-gray-900">
                            {sale.employee.firstName} {sale.employee.lastName}
                          </div>
                          <div className="text-sm text-gray-500">
                            {sale.employee.position}
                          </div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-lg font-bold text-green-600">
                        {formatPrice(sale.total)}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900">
                        {sale.details.length} producto{sale.details.length !== 1 ? 's' : ''}
                      </div>
                      <div className="text-sm text-gray-500">
                        {sale.details.map(detail => detail.productCode).join(', ')}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div className="flex space-x-2">
                        <button
                          onClick={() => handleViewDetails(sale)}
                          className="text-blue-600 hover:text-blue-900 p-1 rounded transition-colors"
                          title="Ver detalles"
                        >
                          <Eye className="h-4 w-4" />
                        </button>
                        <button
                          onClick={() => handleDelete(sale.saleId)}
                          className="text-red-600 hover:text-red-900 p-1 rounded transition-colors"
                          title="Eliminar venta"
                        >
                          <Trash2 className="h-4 w-4" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* Modal para crear venta */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-4xl mx-4 max-h-screen overflow-y-auto">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-semibold text-gray-800">Nueva Venta</h3>
              <button
                onClick={handleCloseModal}
                className="text-gray-400 hover:text-gray-600 transition-colors"
              >
                <X className="h-5 w-5" />
              </button>
            </div>

            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Cliente y Empleado */}
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Cliente
                  </label>
                  <select
                    name="customerId"
                    value={formData.customerId}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
                    required
                  >
                    <option value="">Seleccionar cliente</option>
                    {customers.map((customer) => (
                      <option key={customer.id} value={customer.id}>
                        {customer.firstName} {customer.lastName} - {customer.documentType} {customer.documentNumber}
                      </option>
                    ))}
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Empleado
                  </label>
                  <select
                    name="employeeId"
                    value={formData.employeeId}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
                    required
                  >
                    <option value="">Seleccionar empleado</option>
                    {employees.map((employee) => (
                      <option key={employee.id} value={employee.id}>
                        {employee.firstName} {employee.lastName} - {employee.position}
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              {/* Productos */}
              <div>
                <div className="flex justify-between items-center mb-3">
                  <label className="block text-sm font-medium text-gray-700">
                    Productos
                  </label>
                  <button
                    type="button"
                    onClick={addProductToSale}
                    className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded text-sm flex items-center space-x-1"
                  >
                    <Plus className="h-3 w-3" />
                    <span>Agregar</span>
                  </button>
                </div>

                <div className="space-y-3">
                  {formData.details.map((detail, index) => (
                    <div key={index} className="flex items-center space-x-3 p-3 border rounded-lg">
                      <div className="flex-1">
                        <select
                          value={detail.productId}
                          onChange={(e) => updateProductDetail(index, 'productId', e.target.value)}
                          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
                          required
                        >
                          <option value="">Seleccionar producto</option>
                          {products.map((product) => (
                            <option key={product.id} value={product.id}>
                              {product.productCode} - {product.description} - {formatPrice(product.price)}
                            </option>
                          ))}
                        </select>
                      </div>
                      <div className="w-24">
                        <input
                          type="number"
                          min="1"
                          value={detail.quantity}
                          onChange={(e) => updateProductDetail(index, 'quantity', e.target.value)}
                          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
                          placeholder="Cant."
                          required
                        />
                      </div>
                      <button
                        type="button"
                        onClick={() => removeProductFromSale(index)}
                        className="text-red-600 hover:text-red-800 p-1"
                      >
                        <Trash2 className="h-4 w-4" />
                      </button>
                    </div>
                  ))}
                </div>
              </div>

              <div className="flex space-x-3 pt-4">
                <button
                  type="submit"
                  className="flex-1 bg-orange-600 hover:bg-orange-700 text-white py-2 px-4 rounded-md transition-colors"
                >
                  Crear Venta
                </button>
                <button
                  type="button"
                  onClick={handleCloseModal}
                  className="flex-1 bg-gray-300 hover:bg-gray-400 text-gray-700 py-2 px-4 rounded-md transition-colors"
                >
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Modal de detalles de venta */}
      {showDetailsModal && selectedSale && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-3xl mx-4 max-h-screen overflow-y-auto">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-semibold text-gray-800">
                Detalles de Venta #{selectedSale.saleId}
              </h3>
              <button
                onClick={handleCloseModal}
                className="text-gray-400 hover:text-gray-600 transition-colors"
              >
                <X className="h-5 w-5" />
              </button>
            </div>

            <div className="space-y-6">
              {/* Información general */}
              <div className="grid grid-cols-2 gap-6">
                <div className="border rounded-lg p-4">
                  <h4 className="font-semibold text-gray-800 mb-2 flex items-center">
                    <User className="h-4 w-4 mr-2 text-blue-500" />
                    Cliente
                  </h4>
                  <p className="text-sm text-gray-600">
                    {selectedSale.customer.firstName} {selectedSale.customer.lastName}
                  </p>
                  <p className="text-sm text-gray-500">
                    {selectedSale.customer.documentType} {selectedSale.customer.documentNumber}
                  </p>
                  <p className="text-sm text-gray-500">{selectedSale.customer.email}</p>
                </div>

                <div className="border rounded-lg p-4">
                  <h4 className="font-semibold text-gray-800 mb-2 flex items-center">
                    <UserCheck className="h-4 w-4 mr-2 text-green-500" />
                    Empleado
                  </h4>
                  <p className="text-sm text-gray-600">
                    {selectedSale.employee.firstName} {selectedSale.employee.lastName}
                  </p>
                  <p className="text-sm text-gray-500">{selectedSale.employee.position}</p>
                  <p className="text-sm text-gray-500">{selectedSale.employee.email}</p>
                </div>
              </div>

              {/* Detalles de productos */}
              <div>
                <h4 className="font-semibold text-gray-800 mb-3 flex items-center">
                  <ShoppingCart className="h-4 w-4 mr-2 text-purple-500" />
                  Productos
                </h4>
                <div className="overflow-x-auto">
                  <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                      <tr>
                        <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">
                          Código
                        </th>
                        <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">
                          Marca
                        </th>
                        <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">
                          Precio Unit.
                        </th>
                        <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">
                          Cantidad
                        </th>
                        <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">
                          Subtotal
                        </th>
                      </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-200">
                      {selectedSale.details.map((detail, index) => (
                        <tr key={index}>
                          <td className="px-4 py-2 text-sm font-medium text-gray-900">
                            {detail.productCode}
                          </td>
                          <td className="px-4 py-2 text-sm text-gray-500">
                            {detail.brand}
                          </td>
                          <td className="px-4 py-2 text-sm text-gray-500">
                            {formatPrice(detail.price)}
                          </td>
                          <td className="px-4 py-2 text-sm text-gray-500">
                            {detail.quantity}
                          </td>
                          <td className="px-4 py-2 text-sm font-medium text-green-600">
                            {formatPrice(detail.subtotal)}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>

              {/* Total */}
              <div className="border-t pt-4">
                <div className="flex justify-end">
                  <div className="text-right">
                    <p className="text-lg font-semibold text-gray-800">
                      Total: <span className="text-green-600">{formatPrice(selectedSale.total)}</span>
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div className="flex justify-end pt-4">
              <button
                onClick={handleCloseModal}
                className="bg-gray-300 hover:bg-gray-400 text-gray-700 py-2 px-4 rounded-md transition-colors"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default SalesPage;