package iti.student.foodo.features.onboarding;

import static iti.student.foodo.utils.Animations.fadeIn;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.student.foodo.databinding.BoardItemBinding;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.ViewHolder> {

    private List<BoardItem> items;

    public OnBoardingAdapter(List<BoardItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoardItemBinding binding = BoardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BoardItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        BoardItemBinding binding;

        public ViewHolder(BoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BoardItem item) {
            binding.image.setImageResource(item.getImage());
            binding.title.setText(item.getTitle());
            binding.description.setText(item.getDescription());
            fadeIn(binding.getRoot());
        }
    }

    public void setItems(List<BoardItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
