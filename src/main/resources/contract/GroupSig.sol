pragma solidity ^0.4.24;

import "./GroupSigPrecompiled.sol";

contract GroupSig {
    GroupSigPrecompiled gp;
    bool group_verify_result = false;

    constructor() public {
        gp = GroupSigPrecompiled(0x5004);
    }

    function verify_group_sig(string _sig, string _message, string _gpk_info, string _pbc_param_info) public returns (bool){
        int code = 0;
        (code, group_verify_result) = gp.groupSigVerify(_sig, _message, _gpk_info, _pbc_param_info);
        return group_verify_result;
    }
}
