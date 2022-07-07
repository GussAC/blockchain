package com.globant.blockchain.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.globant.blockchain.model.Block;
import com.globant.blockchain.process.BlockChain;


@RestController
@RequestMapping("/api/blockchain")
public class BlockChainController {
	
	private BlockChain bc;

	public BlockChainController() {
		if (bc == null) {
			bc = new BlockChain();
			bc.genesisBlockChain();
		}  
	}	
		
	
	@GetMapping(path = "/modify/{data}/{position}")
	public Block add(@PathVariable(required=false,name="data") String data,
			@PathVariable(required=false,name="position") int position) {		
		bc.modifyBlockToInvalidate(data, position);		
		return bc.getBlockChain().get(position);
	}

	@GetMapping(path = "/validate")
	public boolean validateBlock() {
		return bc.validateBlock();
	}
	
	@GetMapping(path = "/size")
	public int size() {
		return bc.getBlockChain().size();
	}
	
	@GetMapping(path = "/show",produces = {"application/json"})
	public List<Block> showAllBlockChain() {
		return bc.getBlockChain();
	}	
	
	@RequestMapping(value = "/addblock", method = RequestMethod.POST)
	public ResponseEntity<Block> postAdd(@RequestBody String message) {
		Block block = bc.addBlock(message);
		if(block != null) {
			return new ResponseEntity(block, HttpStatus.CREATED);
		} else {
			return new ResponseEntity(block, HttpStatus.CONFLICT);
		}		
	}		
	
}
