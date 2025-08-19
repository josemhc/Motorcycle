import React from 'react';
import { Users, UserCheck, Package, ShoppingCart, Home } from 'lucide-react';

const Navbar = ({ currentSection, setCurrentSection }) => {
  const navItems = [
    { id: 'home', name: 'Inicio', icon: Home },
    { id: 'customers', name: 'Clientes', icon: Users },
    { id: 'employees', name: 'Empleados', icon: UserCheck },
    { id: 'products', name: 'Productos', icon: Package },
    { id: 'sales', name: 'Ventas', icon: ShoppingCart }
  ];

  return (
    <nav className="bg-blue-600 text-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          <div className="flex items-center space-x-2">
            <Package className="h-8 w-8" />
            <span className="text-xl font-bold">Sistema CRUD</span>
          </div>
          <div className="flex space-x-1">
            {navItems.map((item) => {
              const Icon = item.icon;
              return (
                <button
                  key={item.id}
                  onClick={() => setCurrentSection(item.id)}
                  className={`flex items-center space-x-2 px-4 py-2 rounded-md transition-colors ${
                    currentSection === item.id
                      ? 'bg-blue-700 text-white'
                      : 'text-blue-100 hover:bg-blue-500 hover:text-white'
                  }`}
                >
                  <Icon className="h-4 w-4" />
                  <span>{item.name}</span>
                </button>
              );
            })}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;