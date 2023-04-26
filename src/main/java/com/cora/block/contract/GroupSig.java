package com.cora.block.contract;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class GroupSig extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405260008060146101000a81548160ff02191690831515021790555034801561002a57600080fd5b506150046000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506104888061007c6000396000f300608060405260043610610041576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063931ff3f614610046575b600080fd5b34801561005257600080fd5b5061017f600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610199565b604051808215151515815260200191505060405180910390f35b600080600090506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663b2c8ac1e878787876040518563ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b8381101561025857808201518184015260208101905061023d565b50505050905090810190601f1680156102855780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b838110156102be5780820151818401526020810190506102a3565b50505050905090810190601f1680156102eb5780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b83811015610324578082015181840152602081019050610309565b50505050905090810190601f1680156103515780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b8381101561038a57808201518184015260208101905061036f565b50505050905090810190601f1680156103b75780820380516001836020036101000a031916815260200191505b50985050505050505050506040805180830381600087803b1580156103db57600080fd5b505af11580156103ef573d6000803e3d6000fd5b505050506040513d604081101561040557600080fd5b810190808051906020019092919080519060200190929190505050600060148291906101000a81548160ff0219169083151502179055508192505050600060149054906101000a900460ff169150509493505050505600a165627a7a72305820d7568e0712e5170243403c34dd979d7282bb2400310015c8e1d7baed971434c00029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405260008060146101000a81548160ff02191690831515021790555034801561002a57600080fd5b506150046000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506104888061007c6000396000f300608060405260043610610041576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680637f23d7c014610046575b600080fd5b34801561005257600080fd5b5061017f600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610199565b604051808215151515815260200191505060405180910390f35b600080600090506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16639be9e392878787876040518563ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b8381101561025857808201518184015260208101905061023d565b50505050905090810190601f1680156102855780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b838110156102be5780820151818401526020810190506102a3565b50505050905090810190601f1680156102eb5780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b83811015610324578082015181840152602081019050610309565b50505050905090810190601f1680156103515780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b8381101561038a57808201518184015260208101905061036f565b50505050905090810190601f1680156103b75780820380516001836020036101000a031916815260200191505b50985050505050505050506040805180830381600087803b1580156103db57600080fd5b505af11580156103ef573d6000803e3d6000fd5b505050506040513d604081101561040557600080fd5b810190808051906020019092919080519060200190929190505050600060148291906101000a81548160ff0219169083151502179055508192505050600060149054906101000a900460ff169150509493505050505600a165627a7a723058206a319a9c6790c89ee744df6a9add901988e0736884317befd60c216d727a3f1d0029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_sig\",\"type\":\"string\"},{\"name\":\"_message\",\"type\":\"string\"},{\"name\":\"_gpk_info\",\"type\":\"string\"},{\"name\":\"_pbc_param_info\",\"type\":\"string\"}],\"name\":\"verify_group_sig\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_VERIFY_GROUP_SIG = "verify_group_sig";

    protected GroupSig(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt verify_group_sig(String _sig, String _message, String _gpk_info, String _pbc_param_info) {
        final Function function = new Function(
                FUNC_VERIFY_GROUP_SIG, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_sig), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_message), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_gpk_info), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_pbc_param_info)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] verify_group_sig(String _sig, String _message, String _gpk_info, String _pbc_param_info, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_VERIFY_GROUP_SIG, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_sig), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_message), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_gpk_info), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_pbc_param_info)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForVerify_group_sig(String _sig, String _message, String _gpk_info, String _pbc_param_info) {
        final Function function = new Function(
                FUNC_VERIFY_GROUP_SIG, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_sig), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_message), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_gpk_info), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_pbc_param_info)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple4<String, String, String, String> getVerify_group_sigInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_VERIFY_GROUP_SIG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple4<String, String, String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (String) results.get(3).getValue()
                );
    }

    public Tuple1<Boolean> getVerify_group_sigOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_VERIFY_GROUP_SIG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public static GroupSig load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new GroupSig(contractAddress, client, credential);
    }

    public static GroupSig deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(GroupSig.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
