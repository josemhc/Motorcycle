package com.Motocicletas.service.implementations;

import com.Motocicletas.ResourceNotFoundException;
import com.Motocicletas.dto.sale.request.SaleDTO;
import com.Motocicletas.dto.saledetails.request.SaleDetailDTO;
import com.Motocicletas.dto.saledetails.response.SaleDetailResponseDTO;
import com.Motocicletas.dto.sale.response.SaleResponseDTO;
import com.Motocicletas.model.*;
import com.Motocicletas.repository.ICustomerRepository;
import com.Motocicletas.repository.IEmployeeRepository;
import com.Motocicletas.repository.IProductRepository;
import com.Motocicletas.repository.ISaleRepository;
import com.Motocicletas.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements ISaleService {

    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public SaleResponseDTO getSaleById (Long id) {

        // Encontramos la venta por id (Si existe, si no lanzamos un error)

        Sale sale = saleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));

        // Guardamos los nombres del cliente y el empleado asociados a la venta

        Customer customer = sale.getCustomer();
        Employee employee = sale.getEmployee();

        // Por cada detalle de la Venta, se va a crear su respectivo DTO, y se agregan a una lista

        List<SaleDetailResponseDTO> detailsDTOs = sale.getSaleDetails().stream().map( detail -> {
            Product p = detail.getProduct();
            double subtotal = p.getPrice() * detail.getAmount();
            return new SaleDetailResponseDTO(
                    p.getProductCode(), p.getBrand(),
                    p.getPrice(), detail.getAmount(), subtotal);
        }).toList();

        // Se crea un DTO de la venta que se envia al cliente con el DTO de los detalles de venta
        return new SaleResponseDTO(sale.getId(), customer, employee, sale.getTotal(), detailsDTOs);
    }

    @Override
    public List<SaleResponseDTO> getSales () {

        //        private Long saleId;
        //        private String customerName;
        //        private String employeeName;
        //        private double total;
        //        private List<SaleDetailResponseDTO> details;

        List<Sale> sales = (List<Sale>) saleRepository.findAll();

        List<SaleResponseDTO> salesDTO = sales.stream().map(sale -> {
            Customer customer = sale.getCustomer();
            Employee employee = sale.getEmployee();

            List<SaleDetailResponseDTO> detailsDTO = sale.getSaleDetails().stream().map( detail -> {
                Product p = detail.getProduct();
                double subtotal = p.getPrice() * detail.getAmount();
                return new SaleDetailResponseDTO(
                        p.getProductCode(), p.getBrand(),
                        p.getPrice(), detail.getAmount(), subtotal);
            }).toList();

            return new SaleResponseDTO(sale.getId(), customer, employee, sale.getTotal(), detailsDTO);
        }).toList();

        return salesDTO;

    }

    @Override
    public Sale createSale(SaleDTO saleDTO) {

        // Aqui se guarda el empleado y el cliente al que esta asociada la venta que se crea, utilizando un DTO para manejar solo los datos necesarios

        Sale sale = new Sale();
        sale.setCustomer(customerRepository.findById(saleDTO.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + saleDTO.getCustomerId())));
        sale.setEmployee(employeeRepository.findById(saleDTO.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + saleDTO.getCustomerId())));

        List<SaleDetail> details = new ArrayList<>();
        double total = 0;

        // Aqui se va a a iterar en una lista de detalles que tiene la venta que se esta creando

        for (SaleDetailDTO detailDTO : saleDTO.getDetails()){
            // Se guarda el producto asociado a ese detalle de venta
            Product product = productRepository.findById(detailDTO.getProductId()).orElseThrow();

            // Se crea el objeto detalle de venta
            SaleDetail detail = new SaleDetail();
            detail.setProduct(product);
            detail.setAmount(detailDTO.getQuantity());
            detail.setUnitPrice(product.getPrice());
            detail.setSale(sale);

            details.add(detail); // Se guarda el detalle a la lista de detalles de la venta

            total += product.getPrice() * detailDTO.getQuantity(); // Se suma a el total la cantidad que se compro del producto por su precio unitario
        }

        sale.setSaleDetails(details);
        sale.setTotal(total);

        return saleRepository.save(sale);
    }

    @Override
    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));
        saleRepository.delete(sale);
    }

}
