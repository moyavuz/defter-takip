export interface IHakedisTuru {
    id?: number;
    ad?: string;
    aciklama?: string;
}

export class HakedisTuru implements IHakedisTuru {
    constructor(public id?: number, public ad?: string, public aciklama?: string) {}
}
