package bestdeals.backend.controller;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    @GetMapping
    public ResponseEntity<String> heloo()
    {
        return ResponseEntity.ok().body("Hi Seller");
    }
}
