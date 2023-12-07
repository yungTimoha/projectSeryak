package com.example.itog.Controller;

import com.example.itog.model.Ord;
import com.example.itog.model.Payment;
import com.example.itog.repositories.OrdRepository;
import com.example.itog.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date; // Импортируйте класс java.sql.Date
import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final OrdRepository ordRepository;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository, OrdRepository ordRepository) {
        this.paymentRepository = paymentRepository;
        this.ordRepository = ordRepository;
    }

    @GetMapping
    public String listPayments(Model model) {
        List<Payment> payments = paymentRepository.findAll();
        model.addAttribute("payments", payments);
        return "payment/list"; // Замените на имя вашего Thymeleaf-шаблона
    }

    @GetMapping("/add")
    public String showAddPaymentForm(Model model) {
        List<Ord> ords = ordRepository.findAll();
        model.addAttribute("ords", ords);
        model.addAttribute("payment", new Payment());
        return "payment/add";
    }

    @PostMapping("/add")
    public String addPayment(@Valid @ModelAttribute("payment") Payment payment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "payment/add"; // Останется на странице добавления с отображением ошибок
        }

        // Создаем новый объект Ord
        Ord ord = new Ord();
        ord.setOrdDate(new Date(System.currentTimeMillis())); // Устанавливаем дату заказа (замените на реальное значение)
        ord.setStatus("В обработке"); // Устанавливаем статус заказа (замените на реальное значение)

        // Устанавливаем другие поля объекта Ord, если необходимо

        // Связываем платеж с заказом
        payment.setOrd(ord);

        // Сохраняем платеж и связанный заказ
        paymentRepository.save(payment);

        return "redirect:/payment"; // Перенаправление на страницу со всеми платежами
    }

    @GetMapping("/edit/{id}")
    public String showEditPaymentForm(@PathVariable("id") Long id, Model model) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + id));
        List<Ord> ords = ordRepository.findAll();
        model.addAttribute("ords", ords);
        model.addAttribute("payment", payment);
        return "payment/edit"; // Замените на имя вашего Thymeleaf-шаблона для редактирования платежа
    }

    @PostMapping("/edit/{id}")
    public String editPayment(@PathVariable("id") Long id, @Valid @ModelAttribute("payment") Payment payment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            payment.setId(id); // Set the ID back to the model to render the edit form correctly.
            return "payment/edit"; // Останется на странице редактирования с отображением ошибок
        }

        paymentRepository.save(payment);
        return "redirect:/payment"; // Перенаправление на страницу со всеми платежами
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable("id") Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + id));

        paymentRepository.delete(payment);
        return "redirect:/payment"; // Перенаправление на страницу со всеми платежами
    }
}
