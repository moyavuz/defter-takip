export interface IDepartman {
    id?: number;
    ad?: string;
}

export class Departman implements IDepartman {
    constructor(public id?: number, public ad?: string) {}
}
