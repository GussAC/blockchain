package com.globant.blockchain.process;

/**
*
* @author gustavo.angeles
*/

import java.util.ArrayList;
import java.util.List;
import com.globant.blockchain.model.Block;
import com.globant.blockchain.utils.OperationFile;
import com.globant.blockchain.utils.Utils;

public class BlockChain {
    
    private List<Block> blockChain;
    private OperationFile opFile;
    
    public BlockChain() {
        opFile = new OperationFile();
        opFile.generateFile();  	
    }

	/**
     * Create the first block in block chain
     */
    public void genesisBlockChain() {    	
        blockChain = new ArrayList<>();
        String prev_hash = "00" + Utils.randomHash(62);
        Block block = new Block(prev_hash, "Genesis", -1);
        String hash = miningBlock(block);
        if (hash != null) {
            block.setHash(hash);
            blockChain.add(block);                      
            opFile.writeFileCSV(block.toString());
        }
    }

    /**
     * 
     * @param message
     * @param position
     * @return Object Block
     * @description Modify the message in specific Block
     * 
     */
    public Block modifyBlockToInvalidate(String message, int position) {
        Block b = blockChain.get(position);
        b.setMensaje(message);
        return b;
    }
    
    /**
     * @description Add new block to Block Chain
     * @param mensaje
     * @return
     */
    public Block addBlock(String message) {
        Block block = new Block(blockChain.get(blockChain.size() - 1).getHash(), message, -1);
        String hash = miningBlock(block);
        if (hash != null) {
            block.setHash(hash);
            blockChain.add(block);
            //Add new information to file csv
            opFile.writeFileCSV(block.toString());
        }
        return block;
    }    

	/**
     * @description Search the specific Hash to new Block, the hash must start with '00'
     * @param block
     * @return
     */
    public String miningBlock(Block block) {
        int prefix = 2;
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        String hash = null;
        try {
            do {
                block.setOnce(block.getOnce() + 1);
                String data
                        = block.getPrev_hash()
                        + block.getMensaje()
                        + Integer.toString(block.getOnce());
                hash = Utils.generateHash(data);
            } while (!hash.substring(0, prefix).equals(prefixString));
        } catch (Exception e) {
            System.out.println("INFO Exception: " + e.getMessage());
            System.out.println("INFO Cause: " + e.getCause());
            return null;
        }
        return hash;
    }

    /**
     * @description Verify the hash for each one blocks 
     * @return
     */
    public boolean validateBlock() {
        for (int i = 0; i < blockChain.size()-1; i++) {            
            String hash = blockChain.get(i).getHash();                    
            String data = blockChain.get(i).toString().replace(",", "");
            String hashVerify = Utils.generateHash(data);
            if(!hash.equals(hashVerify)){
                return false;
            }
        }
        return true;
    }

    public List<Block> getBlockChain() {
        return blockChain;
    }


}
