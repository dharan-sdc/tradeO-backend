package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.WithdrawalStatus;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.Withdrawal;
import com.sdc.tradeo.respository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);

        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal procedWithwithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawalOpt = withdrawalRepository.findById(withdrawalId);

        if (withdrawalOpt.isEmpty()) {
            throw new Exception("Withdrawal request not found");
        }

        Withdrawal withdrawal = withdrawalOpt.get();
        withdrawal.setDate(LocalDateTime.now());

        if (accept) {
            withdrawal.setStatus(WithdrawalStatus.SUCCESS);
            sendWithdrawalSuccessEmail(withdrawal.getUser(), withdrawal.getAmount());
        } else {
            withdrawal.setStatus(WithdrawalStatus.PENDING);
            sendWithdrawalRejectionEmail(withdrawal.getUser(), withdrawal.getAmount());
        }

        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getUserWithdrawalHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepository.findAll();
    }

    private void sendWithdrawalSuccessEmail(User user, Long amount) {
        String subject = "Confirmation: Your Withdrawal Request Has Been Processed";
        String body = "Dear " + user.getFullName() + ",\n\n"
                + "We are pleased to inform you that your withdrawal request of ₹" + amount + " has been successfully processed.\n\n"
                + "Transaction Details:\n"
                + "• Amount: ₹" + amount + "\n"
                + "• Date & Time: " + LocalDateTime.now() + "\n"
                + "• Status: SUCCESS\n\n"
                + "If you did not initiate this transaction, please contact our customer support immediately.\n\n"
                + "Thank you for choosing TradeO.\n\n"
                + "Best regards,\n"
                + "TradeO Customer Support\n"
                + "Email: support@tradeo.com\n"
                + "Website: www.tradeo.com";

        sendEmail(user.getEmail(), subject, body);
    }

    private void sendWithdrawalRejectionEmail(User user, Long amount) {
        String subject = "Important Notice: Your Withdrawal Request Was Declined";
        String body = "Dear " + user.getFullName() + ",\n\n"
                + "We regret to inform you that your withdrawal request of ₹" + amount + " has been declined due to one or more of the following reasons:\n"
                + "• Insufficient account balance\n"
                + "• Verification issues\n"
                + "• Other banking/security reasons\n\n"
                + "Transaction Details:\n"
                + "• Amount: ₹" + amount + "\n"
                + "• Date & Time: " + LocalDateTime.now() + "\n"
                + "• Status: REJECTED\n\n"
                + "For further assistance, please contact our customer support team.\n\n"
                + "Best regards,\n"
                + "TradeO Customer Support\n"
                + "Email: support@tradeo.com\n"
                + "Website: www.tradeo.com";

        sendEmail(user.getEmail(), subject, body);
    }


    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
