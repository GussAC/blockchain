package com.globant.blockchain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.globant.blockchain.model.Block;
import com.globant.blockchain.process.BlockChain;
import com.globant.blockchain.utils.Utils;

@SpringBootTest
class BlockchainApplicationTests {

	private BlockChain bc;
	private List<Block> lst;
	@BeforeEach
	public void before() {
		bc = new BlockChain();
		bc.genesisBlockChain();		
	}
	
	@Test
	void contextLoads() {
	}

	@Test
	public void withBlockchainInit_VerifyPrefixHashMining() {
		lst = bc.getBlockChain();
        Block block = new Block(lst.get(lst.size() - 1).getHash(), "NuevoMensaje", -1);        
        String hash = bc.miningBlock(block);		
        assertTrue(hash.substring(0, 2).equals("00"));	   
	}

	@Test
	public void withBlockchainInit_VerifyHashNotNull() {
		lst = bc.getBlockChain();
        Block block = new Block(lst.get(lst.size() - 1).getHash(), "Hola Mundo", -1);
        assertNotNull(block);	
        String hash = bc.miningBlock(block);		
        assertNotNull(hash);
	}
	
	@Test
	public void Testing_generatedHash() {
		String hashExpected = "77dfc0b4b33669e5f9819bc2d5be0ad933d346d40e9db5a976f54acc8f22a339";
		String message = "Mensaje Hola Mundo 100";
		assertEquals(Utils.generateHash(message), hashExpected);		
	}
}
