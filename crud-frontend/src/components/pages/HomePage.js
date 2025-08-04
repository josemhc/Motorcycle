import React from 'react';
import { Users, UserCheck, Package, ShoppingCart } from 'lucide-react';

const HomePage = () => {
  return (
    <div className="max-w-4xl mx-auto py-8 px-4">
      <div className="text-center mb-8">
        <h1 className="text-4xl font-bold text-gray-800 mb-4">
          Sistema de Gestión CRUD
        </h1>
        <p className="text-xl text-gray-600">
          Administra clientes, empleados, productos y ventas desde una sola plataforma
        </p>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-blue-500">
          <div className="flex items-center mb-4">
            <Users className="h-8 w-8 text-blue-500 mr-3" />
            <h3 className="text-xl font-semibold text-gray-800">Clientes</h3>
          </div>
          <p className="text-gray-600">
            Gestiona la información de tus clientes, incluyendo datos de contacto y historial.
          </p>
        </div>
        
        <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-green-500">
          <div className="flex items-center mb-4">
            <UserCheck className="h-8 w-8 text-green-500 mr-3" />
            <h3 className="text-xl font-semibold text-gray-800">Empleados</h3>
          </div>
          <p className="text-gray-600">
            Administra el personal de tu empresa con información completa de cada empleado.
          </p>
        </div>
        
        <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-purple-500">
          <div className="flex items-center mb-4">
            <Package className="h-8 w-8 text-purple-500 mr-3" />
            <h3 className="text-xl font-semibold text-gray-800">Productos</h3>
          </div>
          <p className="text-gray-600">
            Controla tu inventario, precios y características de todos tus productos.
          </p>
        </div>
        
        <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-orange-500">
          <div className="flex items-center mb-4">
            <ShoppingCart className="h-8 w-8 text-orange-500 mr-3" />
            <h3 className="text-xl font-semibold text-gray-800">Ventas</h3>
          </div>
          <p className="text-gray-600">
            Registra y consulta todas las transacciones de venta realizadas.
          </p>
        </div>
      </div>
    </div>
  );
};

export default HomePage;