package com.Motocicletas.service.implementations;

import com.Motocicletas.DTO.request.SaleDTO;
import com.Motocicletas.DTO.request.SaleDetailDTO;
import com.Motocicletas.DTO.response.SaleDetailResponseDTO;
import com.Motocicletas.DTO.response.SaleResponseDTO;
import com.Motocicletas.model.Product;
import com.Motocicletas.model.Sale;
import com.Motocicletas.model.SaleDetail;
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

    public SaleResponseDTO getSaleById (Long id) {

        // Encontramos la venta por id (Si existe, si no lanzamos un error)

        Sale sale = saleRepository.findById(id).orElseThrow();

        // Guardamos los nombres del cliente y el empleado asociados a la venta
        String customerName = sale.getCustomer().getFirstName() + " " + sale.getCustomer().getLastName();
        String employeeName = sale.getEmployee().getFirstName() + " " + sale.getEmployee().getLastName();

        List<SaleDetailResponseDTO> detailsDTOs = sale.getSaleDetails().stream().map( detail -> {
            Product p = detail.getProduct();
            double subtotal = p.getPrice() * detail.getAmount();
            return new SaleDetailResponseDTO(
                    p.getProductCode(), p.getBrand(),
                    p.getPrice(), detail.getAmount(), subtotal);
        }).toList();
        return new SaleResponseDTO(sale.getId(), customerName, employeeName, sale.getTotal(), detailsDTOs);
    }

    @Override
    public Sale createSale(SaleDTO saleDTO) {

        // Aqui se guarda el empleado y el cliente al que esta asociada la venta que se crea, utilizando un DTO para manejar solo los datos necesarios

        Sale sale = new Sale();
        sale.setCustomer(customerRepository.findById(saleDTO.getClientId()).orElseThrow());
        sale.setEmployee(employeeRepository.findById(saleDTO.getEmployeeId()).orElseThrow());

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
}
