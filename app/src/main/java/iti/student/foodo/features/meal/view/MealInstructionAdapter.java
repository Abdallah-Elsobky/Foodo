package iti.student.foodo.features.meal.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.student.foodo.data.model.domain.Instruction;
import iti.student.foodo.databinding.InstructionItemBinding;

public class MealInstructionAdapter extends RecyclerView.Adapter<MealInstructionAdapter.ViewHolder> {

    InstructionItemBinding binding;
    List<Instruction> instructions;

    public MealInstructionAdapter(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = InstructionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(instructions.get(position), position);
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull InstructionItemBinding binding) {
            super(binding.getRoot());
        }

        public void bind(Instruction instruction, int position) {
            binding.instructionNum.setText(String.format("%d", position + 1));
            binding.tvAmount.setText(instruction.getStep());
        }
    }
}
