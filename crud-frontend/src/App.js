import React, { useState } from 'react';
import Navbar from './components/layout/Navbar';
import HomePage from './components/pages/HomePage';
import CustomersPage from './components/pages/CustomersPage';
import EmployeesPage from './components/pages/EmployeesPage';
import ProductsPage from './components/pages/ProductsPage';
import SalesPage from './components/pages/SalesPage';

const App = () => {
  const [currentSection, setCurrentSection] = useState('home');

  const renderContent = () => {
    switch (currentSection) {
      case 'home':
        return <HomePage />;
      case 'customers':
        return <CustomersPage />;
      case 'employees':
        return <EmployeesPage />;
      case 'products':
        return <ProductsPage />;
      case 'sales':
        return <SalesPage />;
      default:
        return <HomePage />;
    }
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar currentSection={currentSection} setCurrentSection={setCurrentSection} />
      {renderContent()}
    </div>
  );
};

export default App;