/*
 * Copyright (c) 2016, Oracle and/or its affiliates.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oracle.truffle.llvm.parser.factories;

import java.util.HashMap;
import java.util.Map;

import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.llvm.nodes.api.LLVMExpressionNode;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMAbortFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMAtExitFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMAbsFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMCeilFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMExpFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMFAbsFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMFloorFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMLAbsFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMLog10Factory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMLogFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMPowFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMRintFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMCMathsIntrinsicsFactory.LLVMSqrtFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMExitFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMSignalFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMTruffleOnlyIntrinsicsFactory.LLVMStrCmpFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMTruffleOnlyIntrinsicsFactory.LLVMStrlenFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.c.LLVMTruffleReadBytesFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleAddressToFunctionFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleBinaryFactory.LLVMTruffleHasSizeFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleBinaryFactory.LLVMTruffleIsBoxedFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleBinaryFactory.LLVMTruffleIsExecutableFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleBinaryFactory.LLVMTruffleIsNullFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleExecuteFactory.LLVMTruffleExecuteBFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleExecuteFactory.LLVMTruffleExecuteCFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleExecuteFactory.LLVMTruffleExecuteDFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleExecuteFactory.LLVMTruffleExecuteFFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleExecuteFactory.LLVMTruffleExecuteIFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleExecuteFactory.LLVMTruffleExecuteLFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleExecuteFactory.LLVMTruffleExecutePFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleGetSizeFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleImportCachedFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleImportFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleInvokeFactory.LLVMTruffleInvokeBFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleInvokeFactory.LLVMTruffleInvokeCFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleInvokeFactory.LLVMTruffleInvokeDFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleInvokeFactory.LLVMTruffleInvokeFFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleInvokeFactory.LLVMTruffleInvokeIFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleInvokeFactory.LLVMTruffleInvokeLFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleInvokeFactory.LLVMTruffleInvokePFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleIsTruffleObjectFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleManagedMallocFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadBFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadCFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadDFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadFFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIdxBFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIdxCFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIdxDFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIdxFFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIdxIFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIdxLFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadIdxPFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadLFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadFactory.LLVMTruffleReadPFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadNBytesFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadNStringFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleReadStringFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleUnboxFactory.LLVMTruffleUnboxBFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleUnboxFactory.LLVMTruffleUnboxCFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleUnboxFactory.LLVMTruffleUnboxDFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleUnboxFactory.LLVMTruffleUnboxFFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleUnboxFactory.LLVMTruffleUnboxIFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleUnboxFactory.LLVMTruffleUnboxLFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteBFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteCFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteDFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteFFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIdxBFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIdxCFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIdxDFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIdxFFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIdxIFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIdxLFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteIdxPFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWriteLFactory;
import com.oracle.truffle.llvm.nodes.intrinsics.interop.LLVMTruffleWriteFactory.LLVMTruffleWritePFactory;

/**
 * This class creates intrinsic functions and is designed to be inherited.
 */
public class LLVMRuntimeIntrinsicFactory {

    public static Map<String, NodeFactory<? extends LLVMExpressionNode>> getFunctionSubstitutionFactories() {
        return new LLVMRuntimeIntrinsicFactory().getFactories();
    }

    protected final Map<String, NodeFactory<? extends LLVMExpressionNode>> intrinsics;

    protected LLVMRuntimeIntrinsicFactory() {
        intrinsics = new HashMap<>();
    }

    protected Map<String, NodeFactory<? extends LLVMExpressionNode>> getFactories() {
        intrinsifyAbortIntrinsics();
        intrinsifyMathFunctions();
        intrinsifyTruffleOnlyIntrinsics();
        intrinsifyInteropIntrinsics();
        return intrinsics;
    }

    protected void intrinsifyInteropIntrinsics() {
        intrinsics.put("@truffle_import", LLVMTruffleImportFactory.getInstance());
        intrinsics.put("@truffle_import_cached", LLVMTruffleImportCachedFactory.getInstance());
        intrinsics.put("@truffle_address_to_function", LLVMTruffleAddressToFunctionFactory.getInstance());

        intrinsics.put("@truffle_read", LLVMTruffleReadPFactory.getInstance());
        intrinsics.put("@truffle_read_i", LLVMTruffleReadIFactory.getInstance());
        intrinsics.put("@truffle_read_l", LLVMTruffleReadLFactory.getInstance());
        intrinsics.put("@truffle_read_c", LLVMTruffleReadCFactory.getInstance());
        intrinsics.put("@truffle_read_f", LLVMTruffleReadFFactory.getInstance());
        intrinsics.put("@truffle_read_d", LLVMTruffleReadDFactory.getInstance());
        intrinsics.put("@truffle_read_b", LLVMTruffleReadBFactory.getInstance());

        intrinsics.put("@truffle_read_idx", LLVMTruffleReadIdxPFactory.getInstance());
        intrinsics.put("@truffle_read_idx_i", LLVMTruffleReadIdxIFactory.getInstance());
        intrinsics.put("@truffle_read_idx_l", LLVMTruffleReadIdxLFactory.getInstance());
        intrinsics.put("@truffle_read_idx_c", LLVMTruffleReadIdxCFactory.getInstance());
        intrinsics.put("@truffle_read_idx_f", LLVMTruffleReadIdxFFactory.getInstance());
        intrinsics.put("@truffle_read_idx_d", LLVMTruffleReadIdxDFactory.getInstance());
        intrinsics.put("@truffle_read_idx_b", LLVMTruffleReadIdxBFactory.getInstance());

        intrinsics.put("@truffle_write", LLVMTruffleWritePFactory.getInstance());
        intrinsics.put("@truffle_write_i", LLVMTruffleWriteIFactory.getInstance());
        intrinsics.put("@truffle_write_l", LLVMTruffleWriteLFactory.getInstance());
        intrinsics.put("@truffle_write_c", LLVMTruffleWriteCFactory.getInstance());
        intrinsics.put("@truffle_write_f", LLVMTruffleWriteFFactory.getInstance());
        intrinsics.put("@truffle_write_d", LLVMTruffleWriteDFactory.getInstance());
        intrinsics.put("@truffle_write_b", LLVMTruffleWriteBFactory.getInstance());

        intrinsics.put("@truffle_write_idx", LLVMTruffleWriteIdxPFactory.getInstance());
        intrinsics.put("@truffle_write_idx_i", LLVMTruffleWriteIdxIFactory.getInstance());
        intrinsics.put("@truffle_write_idx_l", LLVMTruffleWriteIdxLFactory.getInstance());
        intrinsics.put("@truffle_write_idx_c", LLVMTruffleWriteIdxCFactory.getInstance());
        intrinsics.put("@truffle_write_idx_f", LLVMTruffleWriteIdxFFactory.getInstance());
        intrinsics.put("@truffle_write_idx_d", LLVMTruffleWriteIdxDFactory.getInstance());
        intrinsics.put("@truffle_write_idx_b", LLVMTruffleWriteIdxBFactory.getInstance());

        intrinsics.put("@truffle_invoke", LLVMTruffleInvokePFactory.getInstance());
        intrinsics.put("@truffle_invoke_i", LLVMTruffleInvokeIFactory.getInstance());
        intrinsics.put("@truffle_invoke_l", LLVMTruffleInvokeLFactory.getInstance());
        intrinsics.put("@truffle_invoke_c", LLVMTruffleInvokeCFactory.getInstance());
        intrinsics.put("@truffle_invoke_f", LLVMTruffleInvokeFFactory.getInstance());
        intrinsics.put("@truffle_invoke_d", LLVMTruffleInvokeDFactory.getInstance());
        intrinsics.put("@truffle_invoke_b", LLVMTruffleInvokeBFactory.getInstance());

        intrinsics.put("@truffle_execute", LLVMTruffleExecutePFactory.getInstance());
        intrinsics.put("@truffle_execute_i", LLVMTruffleExecuteIFactory.getInstance());
        intrinsics.put("@truffle_execute_l", LLVMTruffleExecuteLFactory.getInstance());
        intrinsics.put("@truffle_execute_c", LLVMTruffleExecuteCFactory.getInstance());
        intrinsics.put("@truffle_execute_f", LLVMTruffleExecuteFFactory.getInstance());
        intrinsics.put("@truffle_execute_d", LLVMTruffleExecuteDFactory.getInstance());
        intrinsics.put("@truffle_execute_b", LLVMTruffleExecuteBFactory.getInstance());

        intrinsics.put("@truffle_unbox_i", LLVMTruffleUnboxIFactory.getInstance());
        intrinsics.put("@truffle_unbox_l", LLVMTruffleUnboxLFactory.getInstance());
        intrinsics.put("@truffle_unbox_c", LLVMTruffleUnboxCFactory.getInstance());
        intrinsics.put("@truffle_unbox_f", LLVMTruffleUnboxFFactory.getInstance());
        intrinsics.put("@truffle_unbox_d", LLVMTruffleUnboxDFactory.getInstance());
        intrinsics.put("@truffle_unbox_b", LLVMTruffleUnboxBFactory.getInstance());

        intrinsics.put("@truffle_is_executable", LLVMTruffleIsExecutableFactory.getInstance());
        intrinsics.put("@truffle_is_null", LLVMTruffleIsNullFactory.getInstance());
        intrinsics.put("@truffle_has_size", LLVMTruffleHasSizeFactory.getInstance());
        intrinsics.put("@truffle_is_boxed", LLVMTruffleIsBoxedFactory.getInstance());

        intrinsics.put("@truffle_get_size", LLVMTruffleGetSizeFactory.getInstance());

        intrinsics.put("@truffle_read_string", LLVMTruffleReadStringFactory.getInstance());
        intrinsics.put("@truffle_read_n_string", LLVMTruffleReadNStringFactory.getInstance());
        intrinsics.put("@truffle_read_bytes", LLVMTruffleReadBytesFactory.getInstance());
        intrinsics.put("@truffle_read_n_bytes", LLVMTruffleReadNBytesFactory.getInstance());

        intrinsics.put("@truffle_is_truffle_object", LLVMTruffleIsTruffleObjectFactory.getInstance());

        intrinsics.put("@truffle_managed_malloc", LLVMTruffleManagedMallocFactory.getInstance());
    }

    /**
     *
     * This method intrinsifies functions that exit the process such as <code>abort</code> or
     * <code>exit</code> in C or <code>_gfortran_abort</code> in Fortran. Not intrinsifying these
     * functions and directly executing the JVM process upon their invocation would not desirable,
     * since cleanups or exit functions (such as shared library destructors or functions registered
     * by <code>atexit</code>) could not be executed. Additionally, a failing JUnit test would then
     * also exit the process instead of executing the remaining test cases.
     *
     */
    protected void intrinsifyAbortIntrinsics() {
        // Fortran
        intrinsics.put("@_gfortran_abort", LLVMAbortFactory.getInstance());
        // C
        intrinsics.put("@abort", LLVMAbortFactory.getInstance());
        intrinsics.put("@exit", LLVMExitFactory.getInstance());
        intrinsics.put("@atexit", LLVMAtExitFactory.getInstance());
        intrinsics.put("@signal", LLVMSignalFactory.getInstance());
    }

    /**
     * Intrinsifies functions that provide an implementation for <code>TruffleObject</code>s but use
     * the Graal NFI if the arguments are not <code>TruffleObject</code>s.
     */
    protected void intrinsifyTruffleOnlyIntrinsics() {
        intrinsics.put("@strlen", LLVMStrlenFactory.getInstance());
        intrinsics.put("@strcmp", LLVMStrCmpFactory.getInstance());
    }

    /**
     * This method intrinsifies functions from the <code>math.h</code> header file of the C standard
     * library. Intrinsifications of these functions (e.g. the C function <code>exp</code>)
     * especially make sense when Graal intrinsifies the corresponding Java method calls, e.g.
     * {@link java.lang.Math#exp(double)}.
     */
    protected void intrinsifyMathFunctions() {
        intrinsics.put("@sqrt", LLVMSqrtFactory.getInstance());
        intrinsics.put("@log", LLVMLogFactory.getInstance());
        intrinsics.put("@log10", LLVMLog10Factory.getInstance());
        intrinsics.put("@rint", LLVMRintFactory.getInstance());
        intrinsics.put("@ceil", LLVMCeilFactory.getInstance());
        intrinsics.put("@floor", LLVMFloorFactory.getInstance());
        intrinsics.put("@abs", LLVMAbsFactory.getInstance());
        intrinsics.put("@labs", LLVMLAbsFactory.getInstance());
        intrinsics.put("@fabs", LLVMFAbsFactory.getInstance());
        intrinsics.put("@pow", LLVMPowFactory.getInstance());
        intrinsics.put("@exp", LLVMExpFactory.getInstance());
    }

}
