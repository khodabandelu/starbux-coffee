package com.khodabandelu.starbux.cart.api.controller;

import com.khodabandelu.starbux.cart.api.commands.AddItemCommand;
import com.khodabandelu.starbux.cart.api.commands.ConfirmCartCommand;
import com.khodabandelu.starbux.cart.api.commands.CreateCartCommand;
import com.khodabandelu.starbux.cart.api.commands.RemoveItemCommand;
import com.khodabandelu.starbux.cart.api.dto.CreateCartResponse;
import com.khodabandelu.cqrs.core.exceptions.AggregateNotFoundException;
import com.khodabandelu.cqrs.core.infrastructure.CommandDispatcher;
import com.khodabandelu.starbux.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final Logger logger = Logger.getLogger(CartController.class.getName());
    private final CommandDispatcher commandDispatcher;

    /**
     * {@code POST  /api/v1/cart } : create cart
     * @param command the command with initial data to create cart.
     * @throws IllegalStateException {@code 400 (Bad Request)} if the data is not correct.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with id of cart and with message successfully.
     * */
    @PostMapping
    public ResponseEntity<BaseResponse> createCart(@RequestBody CreateCartCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new CreateCartResponse("Cart creation request completed successfully!", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrMessage = MessageFormat.format("Error while processing request to create new cart for id = {0} ", id);
            logger.log(Level.SEVERE, safeErrMessage);
            return new ResponseEntity<>(new CreateCartResponse(safeErrMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code POST  /api/v1/cart/id/addItem } : add item to cart
     * @param id the id of cart that you want to add new item to it.
     * @param command the command with data to add item to cart.
     * @throws IllegalStateException {@code 400 (Bad Request)} if the data is not correct.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with message successfully.
     * */
    @PostMapping("/{id}/addItem")
    public ResponseEntity<BaseResponse> addItem(@PathVariable("id") String id, @RequestBody AddItemCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Add item to cart request completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrMessage = MessageFormat.format("Error while processing request to add item to cart with id = {0} ", id);
            logger.log(Level.SEVERE, safeErrMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code POST  /api/v1/cart/id/removeItem } : remove item from cart
     * @param id the id of cart that you want to remove item from it.
     * @param command the command with data to remove item from cart.
     * @throws IllegalStateException {@code 400 (Bad Request)} if the data is not correct.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with message successfully.
     * */
    @PostMapping("/{id}/removeItem")
    public ResponseEntity<BaseResponse> removeItem(@PathVariable("id") String id, @RequestBody RemoveItemCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Remove item to cart request completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrMessage = MessageFormat.format("Error while processing request to remove item to cart with id = {0} ", id);
            logger.log(Level.SEVERE, safeErrMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code POST  /api/v1/cart/id/confirmCart } : confirm cart.
     * @param id the id of cart that you want to confirm it.
     * @throws IllegalStateException {@code 400 (Bad Request)} if the data is not correct.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with message successfully.
     * */
    @PostMapping("/{id}/confirmCart")
    public ResponseEntity<BaseResponse> confirmCart(@PathVariable("id") String id) {
        try {
            commandDispatcher.send(new ConfirmCartCommand(id));
            return new ResponseEntity<>(new BaseResponse("Confirm cart request completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrMessage = MessageFormat.format("Error while processing request to confirm cart for id = {0} ", id);
            logger.log(Level.SEVERE, safeErrMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
